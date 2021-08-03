package org.rhett.mysecurity.config;

import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.security.SysUserDetailsServiceImpl;
import org.rhett.mysecurity.security.handler.*;
import org.rhett.mysecurity.utils.rsa.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * spring security配置
 */
@Configuration
@EnableWebSecurity //开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解，如：@PreAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RsaKeyProperties properties;
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private MyAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private KaptchaFilter kaptchaFilter;
    @Autowired
    private SysUserDetailsServiceImpl sysUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(sysUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 权限配置
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域，关闭跨站请求保护
        http.cors().and().csrf().disable()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler).permitAll()
                .and()
                //用户授权管理，配置拦截规则
                .authorizeRequests()
                //跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //druid连接池SQL监控，如果有的话
                //.antMatchers("/druid/**").permitAll()
                //swagger相关
                .antMatchers(SysConstant.SWAGGER_WHITELIST).permitAll()
                //首页、登录、验证码页面等
                .antMatchers(SysConstant.URL_WHITELIST).permitAll()
                //服务监控
                .antMatchers("/actuator/**").permitAll()
                //其他所有请求需要身份验证
                .anyRequest().authenticated()
                .and()
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //添加自定义的token验证过滤器
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), properties), UsernamePasswordAuthenticationFilter.class)
                //验证码过滤器
                //.addFilterBefore(kaptchaFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权未登录异常处理
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                //权限不足处理
                .accessDeniedHandler(accessDeniedHandler);

        //防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }

    /**
     * 认证管理器
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 密码编码器
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
