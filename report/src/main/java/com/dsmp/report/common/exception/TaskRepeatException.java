package com.dsmp.report.common.exception;

import com.dsmp.common.enums.strategy.DsmpResponseCodeEnum;
import com.dsmp.common.exception.BusinessException;

/**
 * @author byliu
 **/
public class TaskRepeatException extends BusinessException {
    public TaskRepeatException() {
    }

    public TaskRepeatException(String message) {
        super(message);
    }


    public TaskRepeatException(DsmpResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.name());
    }
}
