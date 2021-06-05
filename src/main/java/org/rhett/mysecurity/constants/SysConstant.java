package org.rhett.mysecurity.constants;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 项目相关常量
 */
public class SysConstant {
    /**
     * 验证码的key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户名
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    public static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * Swagger2白名单
     */
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            //knife4j
            "/doc.html",
    };

    /**
     * 其他白名单
     */
    public static final String[] URL_WHITELIST = {
            "/login",
            "/",
            "/kaptcha",

            "/favicon.ico",
    };
}
