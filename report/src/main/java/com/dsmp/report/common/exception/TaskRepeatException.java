package com.dsmp.report.common.exception;

import com.kxingyi.common.exception.BusinessException;

/**
 * @author byliu
 **/
public class TaskRepeatException extends BusinessException {
    public TaskRepeatException() {
    }

    public TaskRepeatException(String message) {
        super(message);
    }

}
