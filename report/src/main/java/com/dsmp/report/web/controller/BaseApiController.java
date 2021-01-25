package com.dsmp.report.web.controller;

import com.dsmp.common.exception.BusinessException;
import com.dsmp.common.web.response.MsgResult;
import com.dsmp.common.web.response.UnifyApiCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: byliu
 * @Date: 2021/1/6 16:50
 **/
@RestController
public class BaseApiController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /*@ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MsgResult handle(ValidationException exception) {
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> item : violations) {
                //打印验证不通过的信息
                sb.append(item.getMessage());
            }
            return new MsgResult(UnifyApiCode.ILLEGALARGUMENT.getCode(), sb.toString());
        }
        return new MsgResult(UnifyApiCode.ILLEGALARGUMENT);
    }*/

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public MsgResult handleAllException(Exception e, WebRequest request) {
        logger.error(request.getDescription(false));
        logger.error(e.getMessage(), e);
        return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), "发生未知错误");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {BusinessException.class})
    public MsgResult handleBusinessException(BusinessException e) {
        return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {HttpMessageNotReadableException.class})
    public MsgResult handleBusinessException(HttpMessageNotReadableException e) {
        return MsgResult.message(UnifyApiCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MsgResult validationError(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringJoiner sj = new StringJoiner(",");
        allErrors.forEach(objectError -> sj.add(objectError.getDefaultMessage()));
        return MsgResult.message(UnifyApiCode.ILLEGALARGUMENT.getCode(), sj.toString());
    }


}
