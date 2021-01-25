package com.kxingyi.common.util.minio;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MinioRegister.class)
public @interface EnableMinio {
}
