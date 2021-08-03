package org.rhett.mysecurity.controller;

import org.rhett.mysecurity.common.Result;
import org.rhett.mysecurity.entity.SysUser;
import org.rhett.mysecurity.models.LoginInfo;
import org.rhett.mysecurity.service.SysUserService;
import org.rhett.mysecurity.utils.JwtTokenUtil;
import org.rhett.mysecurity.utils.SecurityUtil;
import org.rhett.mysecurity.utils.rsa.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RsaKeyProperties properties;


    /**
     * 登录操作
     * @param loginInfo 登录对象
     * @return 响应结果封装
     */
    @PostMapping("/login")
    public Result login(@RequestParam LoginInfo loginInfo) {
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        String code = loginInfo.getCode();
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(code)) {
            return Result.error().message("参数不能为空！");
        }
        //验证码已在过滤器中进行校验，这里可以跳过
        SysUser user = sysUserService.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            return Result.error().message("账号不存在！");
        }
        if (!SecurityUtil.matchesPassword(password, user.getPassword())) {
            return Result.error().message("用户名或者密码不正确！");
        }
        String token = JwtTokenUtil.generateToken(user, properties.getPrivateKey());

        return Result.ok().data("token", token);
    }
}
