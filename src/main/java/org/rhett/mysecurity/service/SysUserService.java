package org.rhett.mysecurity.service;

import org.rhett.mysecurity.entity.SysUser;

public interface SysUserService {
    SysUser findByUsername(String username);
}
