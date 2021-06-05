package org.rhett.mysecurity.security.handler;

import org.rhett.mysecurity.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 登陆失败处理方式
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        //获取到异常信息
        Result result = Result.error().message("用户名或密码不正确！");
        JsonHandler.writeJSON(response, result);
    }
}
