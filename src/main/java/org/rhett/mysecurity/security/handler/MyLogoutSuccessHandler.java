package org.rhett.mysecurity.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.rhett.mysecurity.common.Result;
import org.rhett.mysecurity.constants.SysConstant;
import org.rhett.mysecurity.utils.DateTimeUtil;
import org.rhett.mysecurity.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 退出成功的处理方式
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String header = request.getHeader(SysConstant.TOKEN_HEADER);
        // 判断 Authorization 和 类型Bearer 是否为null
        if (!ObjectUtils.isEmpty(header) && header.startsWith(SysConstant.TOKEN_PREFIX)) {
            String authToken = header.substring(SysConstant.JWT_PAYLOAD_USER_KEY.length());
            //将token放入黑名单中,时间是年月日
            redisUtil.hset("blackList", authToken, DateTimeUtil.getTime());
            log.info("用户登出成功！token：{}已加入redis黑名单",authToken);
        }
        SecurityContextHolder.clearContext();
        //返回值
        JsonHandler.writeJSON(response, Result.ok().message("退出成功！"));
    }
}
