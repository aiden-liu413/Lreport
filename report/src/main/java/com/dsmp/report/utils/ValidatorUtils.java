package com.dsmp.report.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author byliu
 **/
public class ValidatorUtils {
    private static Validator validator;
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    public static List<String> validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> validate = validator.validate(object, groups);
        List<String> list = new ArrayList<>();
        for(ConstraintViolation<Object> aa :validate) {
            list.add(aa.getMessage());
        }
        return list;
    }

}
