package org.rhett.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 让用户类实现UserDetails接口
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUser对象", description="用户管理表")
public class SysUser implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "状态 0：启用，1：禁止")
    private Integer status;


    @TableField(exist = false)
    private List<SysRole> sysRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return sysRoles;
    }

    /**
     * 账户是否未过期
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定账户是否解锁
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *指示是否已过期的用户凭证(密码)
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *是否账号启用
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
