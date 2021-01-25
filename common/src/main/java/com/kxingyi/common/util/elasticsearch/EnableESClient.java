package com.kxingyi.common.util.elasticsearch;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ElasticSearchConfig.class)
public @interface EnableESClient {
}
