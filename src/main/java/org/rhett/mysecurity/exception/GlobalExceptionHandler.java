package org.rhett.mysecurity.exception;

import org.rhett.mysecurity.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        logger.error("系统出现异常：{}", e.getMessage());
        return Result.error();
    }

    /**
     * 自定义异常处理
     * @param e 自定义异常
     * @return 响应结果封装
     */
    @ExceptionHandler(BusinessException.class)
    public Result handlerException(BusinessException e) {
        logger.error("服务出现异常：{}", e.getMessage());
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * 自定义验证码异常
     * @param e 验证码异常
     * @return 响应结果封装
     */
    @ExceptionHandler(KaptchaException.class)
    public Result handlerException(KaptchaException e) {
        logger.error("验证码异常：{}", e.getMessage());
        return Result.error().message(e.getMessage());
    }
}
