package com.dsmp.report.config;

import com.dsmp.report.jms.rabbitmq.AbstractAmqpProducer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author byliu
 **/
@Configuration
public class RabbitMQConfig implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        RabbitMQConfig.applicationContext = applicationContext;
        init();
    }

    @Bean
    Exchange getExchangeObj() {
        return new DirectExchange("1111111");
    }

    void init() {
        Map<String, AbstractAmqpProducer> producers = applicationContext.getBeansOfType(AbstractAmqpProducer.class);
        producers.forEach((k, v) -> {
            try {
                Class<?> aClass = v.getClass();
                Exchange exchangeObj = (Exchange) aClass.getMethod("getExchangeObj").invoke(v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }
}
