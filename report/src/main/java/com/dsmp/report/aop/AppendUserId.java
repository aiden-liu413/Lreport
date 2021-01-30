package com.dsmp.report.aop;

import java.lang.annotation.*;

/**
 * @author byliu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppendUserId {
}
