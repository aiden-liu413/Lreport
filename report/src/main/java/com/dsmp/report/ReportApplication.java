package com.dsmp.report;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.kxingyi.common.util.feign.EnableFeignClient;
import com.kxingyi.common.util.minio.EnableMinio;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMinio
@EnableJpaAuditing
@EnableFeignClient
@NacosPropertySource(dataId = "fileCacheDays", groupId="report",autoRefreshed = true)
public class ReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }

    @NacosInjected
    NamingService namingService;
    @Value("${spring.application.name}")
    String applicationName;
    @Value("${server.port}")
    Integer serverPort;
    /*@PostConstruct
    public void registerInstance() throws NacosException {
        if(CollectionUtils.isEmpty(namingService.getAllInstances(applicationName))){
            namingService.registerInstance(applicationName,"127.0.0.1",serverPort);
        }
    }*/
}
