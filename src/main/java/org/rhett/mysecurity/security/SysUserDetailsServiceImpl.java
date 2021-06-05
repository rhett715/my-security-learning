package org.rhett.mysecurity.security;

import org.rhett.mysecurity.entity.SysUser;
import org.rhett.mysecurity.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 用户登录认证信息的查询
 */
@Service
public class SysUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名去数据库中查询指定用户，这就要保证数据库中的用户的名称必须唯一，否则将会报错
        SysUser sysUser = sysUserMapper.findByUsername(username);
        //如果没有查询到这个用户，说明数据库中不存在此用户，认证失败，此时需要抛出用户账户不存在
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("user not exist.");
        }
        return sysUser;
    }
}
