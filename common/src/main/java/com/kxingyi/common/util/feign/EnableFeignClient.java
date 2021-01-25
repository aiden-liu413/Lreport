package com.kxingyi.common.util.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 从此注解的包下开始扫描{@link FeignClient 注入spring}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignClientRegistrar.class)
public @interface EnableFeignClient {
}
