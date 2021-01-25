package com.kxingyi.common.exception;

/**
 * @Author: chengpan
 * @Date: 2019/9/3
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

}
