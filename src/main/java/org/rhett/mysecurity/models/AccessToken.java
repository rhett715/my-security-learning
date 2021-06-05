package org.rhett.mysecurity.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 */
@Data
@Builder
public class AccessToken {
    private String loginAccount;
    private String token;
    private Date expireTime;
}
