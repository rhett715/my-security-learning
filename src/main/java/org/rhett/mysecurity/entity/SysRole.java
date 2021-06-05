package org.rhett.mysecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 让角色类实现GrantedAuthority接口
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysRole对象", description="角色管理表")
public class SysRole implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String desc;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
