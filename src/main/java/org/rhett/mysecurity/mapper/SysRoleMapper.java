package org.rhett.mysecurity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.rhett.mysecurity.entity.SysRole;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    //根据用户编号查询角色列表
    @Select("select r.id, r.name, r.desc from `sys_role` as r where r.id in (" +
            " select ur.role_id from `sys_user_role` as ur where ur.user_id = #{userId}" + ")")
    List<SysRole> findByUserId(Integer userId);
}
