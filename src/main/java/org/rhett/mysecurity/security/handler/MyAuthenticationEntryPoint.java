package org.rhett.mysecurity.security.handler;

import org.rhett.mysecurity.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 用户未登录的处理方式
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        JsonHandler.writeJSON(response, Result.error().code(HttpStatus.UNAUTHORIZED.value()).message("请先登录！"));
    }
}
