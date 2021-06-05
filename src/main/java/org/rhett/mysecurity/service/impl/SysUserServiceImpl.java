package org.rhett.mysecurity.service.impl;

import org.rhett.mysecurity.entity.SysUser;
import org.rhett.mysecurity.mapper.SysUserMapper;
import org.rhett.mysecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {

        return sysUserMapper.findByUsername(username);
    }
}
