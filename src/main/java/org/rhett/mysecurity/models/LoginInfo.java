package org.rhett.mysecurity.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 登录对象
 */
@Data
public class LoginInfo {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
