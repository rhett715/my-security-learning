package org.rhett.mysecurity.security.handler;

import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.exception.BusinessException;
import org.rhett.mysecurity.exception.KaptchaException;
import org.rhett.mysecurity.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 自定义图片验证码过滤器
 * 在用户登录成功之前先要进行验证码的验证，而且只验证一次
 */
@Component
public class KaptchaFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if ("/login".equals(uri) && request.getMethod().equals("POST")) {
            //
            try {
                validateCode(request);
            } catch (KaptchaException e) {
                //交给请求认证失败处理器
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 检验验证码
     * @param request 请求
     * @throws BusinessException 自定义异常
     */
    private void validateCode(HttpServletRequest request) {
        String code = request.getParameter("code");
        if (ObjectUtils.isEmpty(code)) {
            throw new KaptchaException("验证码不存在！");
        }
        if (!code.equals(redisUtil.get(SysConstant.CAPTCHA_CODE_KEY))) {
            throw new KaptchaException("验证码错误！");
        }
        //去掉验证码，一次性使用
        redisUtil.del(SysConstant.CAPTCHA_CODE_KEY);
    }
}
