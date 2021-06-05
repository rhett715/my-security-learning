package org.rhett.mysecurity.utils;

import java.util.UUID;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * ID生成器
 */
public class IDUtil {
    /**
     * 获取随机的UUID
     * @return 随机的UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }


}
