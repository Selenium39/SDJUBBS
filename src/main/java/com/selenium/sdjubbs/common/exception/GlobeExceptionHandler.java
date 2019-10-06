package com.selenium.sdjubbs.common.exception;

import com.selenium.sdjubbs.common.util.ExceptionConstant;
import com.selenium.sdjubbs.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobeExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception e) {
        e.printStackTrace();
        return Result.failure(ExceptionConstant.ExceptionCode, ExceptionConstant.ExceptionReason);
    }
}
