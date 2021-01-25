package com.kxingyi.common.annotation.excelhead;


import com.kxingyi.common.enums.ExportFieldDataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportField {
    String fieldName();

    //使用此注解的字段的数据类型
    ExportFieldDataType fieldDataType();

    //如果fieldDataType为list类型，需要指定集合类型实参里的获取数据的方法，例如权限组名的getName方法
    String listExportMethod() default "";

    //枚举类型导出时调用的方法
    String enumExportMethod() default "toString";

    String objectExportMethod() default "toString";

}
