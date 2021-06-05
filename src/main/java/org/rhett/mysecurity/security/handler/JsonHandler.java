package org.rhett.mysecurity.security.handler;

import cn.hutool.json.JSONUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author Rhett
 * @Date 2021/6/4
 * @Description
 * 封装处理器响应体结果
 */
public class JsonHandler {

    /**
     * 向浏览器响应一个json字符串
     * @param response 响应
     * @param data 响应数据
     * @throws IOException 异常
     */
    public static void writeJSON(HttpServletResponse response, Object data) throws IOException {
        //处理编码方式，防止出现中文乱码
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        //输出JSON
        ServletOutputStream outputStream = response.getOutputStream();
        //使用jackson将对象转为json格式并交给响应
        outputStream.write(JSONUtil.toJsonStr(data).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
