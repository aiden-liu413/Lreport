package com.dsmp.report.aop;

import java.lang.annotation.*;

/**
 * 记录任务执行日志
 *
 * @author byliu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogReportTaskDetail {
}
