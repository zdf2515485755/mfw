package com.zdf.mfwuser.handler;

import com.zdf.internalcommon.result.ResponseResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author mrzhang
 * 统一异常处理
 */
@RestControllerAdvice
@Order(99)
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e)
    {
        return ResponseResult.fail(e.getMessage());
    }
}