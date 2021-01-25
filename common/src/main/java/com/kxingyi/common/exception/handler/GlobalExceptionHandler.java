package com.kxingyi.common.exception.handler;

import com.kxingyi.common.enums.ApiErrorType;
import com.kxingyi.common.exception.BusinessException;
import com.kxingyi.common.web.response.HandleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.StringJoiner;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public HandleResult handleAllException(Exception e, WebRequest request) {
        logger.error(request.getDescription(false));
        logger.error(e.getMessage(), e);
        return HandleResult.message(ApiErrorType.SERVER_ERROR.getDescription(), "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value= {BusinessException.class})
    public HandleResult handleBusinessException(BusinessException e) {
        return HandleResult.message(ApiErrorType.CLIENT_BUSINESS.getDescription(), e.getMessage());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HandleResult validationError(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringJoiner sj = new StringJoiner(",");
        allErrors.forEach(objectError -> sj.add(objectError.getDefaultMessage()));
        return HandleResult.message(ApiErrorType.CLINET_ARG.getDescription(), sj.toString());
    }
}
