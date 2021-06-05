package org.rhett.mysecurity.security.handler;

import io.jsonwebtoken.ExpiredJwtException;
import org.rhett.mysecurity.common.Result;
import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.entity.SysUser;
import org.rhett.mysecurity.utils.JwtTokenUtil;
import org.rhett.mysecurity.utils.rsa.RsaKeyProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 身份验证过滤器，主要是token的检验
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private RsaKeyProperties properties;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RsaKeyProperties properties) {
        super(authenticationManager);
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                //如果token的格式错误，则提示用户非法登录
                chain.doFilter(request, response);
                //ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户非法登录！");
                JsonHandler.writeJSON(response, Result.error().code(HttpServletResponse.SC_FORBIDDEN).message("用户非法登录！"));
            } else {
                //如果token的格式正确，则先要获取到token
                String token = header.replace("Bearer ", "");
                //使用公钥进行解密然后来验证token是否正确
                SysUser sysUser =(SysUser) JwtTokenUtil.parserToken(token, properties.getPublicKey()).get(SysConstant.JWT_PAYLOAD_USER_KEY);
                Boolean isValidate = JwtTokenUtil.validateToken(token, sysUser, properties.getPublicKey());

                if (sysUser != null && isValidate) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null, sysUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                } else {
                    //ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户验证失败！");
                    JsonHandler.writeJSON(response, Result.error().code(HttpServletResponse.SC_FORBIDDEN).message("用户验证失败！"));
                }
            }
        } catch (ExpiredJwtException e) {
            //ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "请您重新登录！");
            JsonHandler.writeJSON(response, Result.error().code(HttpServletResponse.SC_FORBIDDEN).message("请您重新登录！"));
        }
    }
}
