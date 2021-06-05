package org.rhett.mysecurity.utils;

import org.rhett.mysecurity.entity.SysUser;
import org.rhett.mysecurity.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * security安全工具类
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户
     * @return 登录用户对象
     */
    public static SysUser getLoginUser() {
        try {
            return (SysUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), "获取用户信息异常！");
        }
    }

    /**
     * 获取当前登录用户名
     * @return 用户名
     */
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED.value(), "获取用户名异常！");
        }
    }

    /**
     * 获取Authentication
     * @return 认证
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 密码加密
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否相同
     */
    public static Boolean matchesPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
        String password = encryptPassword("123456");

        System.out.println(password);
        System.out.println(matchesPassword("123456", password));
    }
}
