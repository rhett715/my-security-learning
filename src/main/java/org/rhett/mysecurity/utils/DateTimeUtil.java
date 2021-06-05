package org.rhett.mysecurity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Rhett
 * @Date 2021/6/5
 * @Description
 * 日期格式转换工具类
 */
public class DateTimeUtil {
    public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf_date_format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 返回指定格式的日期字符串:yyyy-MM-dd HH:mm:ss
     * @return 日期字符串
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }
}
