package com.kxingyi.common.util.feign;

import feign.Feign;
import feign.Request;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Set;

/**
 * @author hujie
 * @date 2020/5/14 17:07
 * 扫描带{@link FeignClient}的接口注入spring容器
 **/
public class FeignClientRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = new FeignClientScanner();
        scanner.setResourceLoader(resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(FeignClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        /** 从{@link EnableFeignClient 注解的包下开始扫描}*/
        String scanPackageName = ClassUtils.getPackageName(metadata.getClassName());
        Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(scanPackageName);
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@FeignClient只能作用于接口上");
                Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(FeignClient.class.getCanonicalName());

                BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
                String className = annotationMetadata.getClassName();
                definition.addPropertyValue("config", attributes.get("configuration"));
                definition.addPropertyValue("type", className);
                BeanDefinitionHolder newHolder = new BeanDefinitionHolder(definition.getBeanDefinition(), className, null);
                BeanDefinitionReaderUtils.registerBeanDefinition(newHolder, registry);

            }
        }
    }
}

class FeignClientScanner extends ClassPathScanningCandidateComponentProvider {
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        boolean isCandidate = false;
        if (beanDefinition.getMetadata().isIndependent()) {
            if (!beanDefinition.getMetadata().isAnnotation()) {
                isCandidate = true;
            }
        }
        return isCandidate;
    }
}

class FeignClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware {
    private Class<?> type;
    private Class<? extends FeignClientConfiguration> config;
    private ApplicationContext applicationContext;

    @Override
    public Object getObject() {
        return configureFeign();
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    private <T> T configureFeign() {
        FeignClientConfiguration configuration = applicationContext.getBean(config);
        if (configuration == null) {
            throw new RuntimeException("spring中没有" + config.toString() + "类型的bean");
        }
        return (T) Feign.builder()
                .client(configuration.client())
                .encoder(configuration.encoder())
                .options(new Request.Options(1000 * 10, 1000 * 10))
                .decoder(configuration.decoder())
                .retryer(configuration.retryer())
                .requestInterceptors(configuration.requestInterceptor())
                .target(new Target.HardCodedTarget<>(type, configuration.urlPre()));

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Class<? extends FeignClientConfiguration> getConfig() {
        return config;
    }

    public void setConfig(Class<? extends DefaultFeignClientConfiguration> config) {
        this.config = config;
    }

}