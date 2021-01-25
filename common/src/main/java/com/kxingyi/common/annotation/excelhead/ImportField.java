package com.kxingyi.common.annotation.excelhead;


import com.kxingyi.common.web.request.NullCellOption;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImportField {
    String fieldName();

    int fieldCell();

    String comment() default "";

    Class option() default NullCellOption.class;
}
