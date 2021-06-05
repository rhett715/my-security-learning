package org.rhett.mysecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 自定义验证码异常
 */
public class KaptchaException extends AuthenticationException {
    public KaptchaException(String msg) {
        super(msg);
    }
}
