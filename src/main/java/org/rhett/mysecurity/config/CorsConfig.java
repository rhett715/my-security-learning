package org.rhett.mysecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                //放行哪些原始域
                .allowedOriginPatterns("*")
                //放行哪些原始域(头部信息)
                .allowedHeaders("*")
                //放行哪些原始域(请求方式)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //是否发送Cookie信息
                .allowCredentials(true)
                //预检间隔时间
                .maxAge(3600);
    }
}
