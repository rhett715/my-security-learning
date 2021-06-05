package org.rhett.mysecurity.security.handler;

import org.rhett.mysecurity.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 权限不足处理方式
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        Result result = Result.error().code(HttpStatus.FORBIDDEN.value()).message("没有操作权限！");
        JsonHandler.writeJSON(response, result);
    }
}
