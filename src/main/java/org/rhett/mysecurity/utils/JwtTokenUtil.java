package org.rhett.mysecurity.utils;

import io.jsonwebtoken.*;
import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.exception.BusinessException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * JWT工具
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtil {
    private static long tokenExpiration;
    private static String tokenSignKey;

    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] bytes = DatatypeConverter.parseBase64Binary(tokenSignKey);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }


    /**
     * 生成用私钥加密的token
     * @param userDetails 带权限的用户对象
     * @param privateKey 私钥
     * @return 令牌
     */
    public static String generateToken(UserDetails userDetails, PrivateKey privateKey) {
        //载荷包含自定义信息
        Map<String, Object> claims = new HashMap<>();
        claims.put(SysConstant.JWT_USERNAME, userDetails.getUsername());
        claims.put(SysConstant.JWT_CREATED, new Date());
        claims.put(SysConstant.JWT_PAYLOAD_USER_KEY, JacksonUtil.toJsonString(userDetails));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();
    }

    /**
     * 公钥解析token
     * @param token 令牌
     * @param publicKey 公钥
     * @return 声明
     */
    public static Claims parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否有效
     * @param token 令牌
     * @return 是否有效
     */
    public static Boolean validateToken(String token, UserDetails userDetails, PublicKey publicKey) {
        String username = getUsernameFromToken(token, publicKey);
        if (ObjectUtils.isEmpty(username)) return false;
        return  username.equals(userDetails.getUsername()) && !isTokenExpired(token, publicKey);
    }


    /**
     * 从token中获取用户名
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token, PublicKey publicKey) {
        Claims claims = getClaimsFromToken(token, publicKey);
        if (ObjectUtils.isEmpty(claims)) {
            return null;
        }
        return (String) claims.get(SysConstant.JWT_USERNAME);
    }

    /**
     * 判断令牌是否过期
     * @param token 令牌
     * @param publicKey 公钥
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token, PublicKey publicKey) {
        Claims claims = getClaimsFromToken(token, publicKey);
        return claims.getExpiration().before(new Date());
    }


    /**
     * 校验token，并返回声明
     * @param token 令牌
     * @return 声明
     */
    private static Claims getClaimsFromToken(String token, PublicKey publicKey) {
        if (ObjectUtils.isEmpty(token)) {
            //return null;
            throw new BusinessException("用户未登录！");
        }
        try {
            return parserToken(token, publicKey);
        } catch (Exception e) {
            throw new BusinessException("用户未登录！");
        }
    }

}
