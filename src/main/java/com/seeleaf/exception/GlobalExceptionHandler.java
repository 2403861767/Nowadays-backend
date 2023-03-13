package com.seeleaf.exception;

import com.seeleaf.common.BaseResponse;

import com.seeleaf.common.ErrorCode;
import com.seeleaf.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author seeleaf
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("BusinessException: "+ e.getMessage(), e);
        return ResultUtils.error(e.getCode(),e.getMessage(), e.getDescription());
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeException(RuntimeException e){
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(), "");
    }

}
