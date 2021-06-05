package org.rhett.mysecurity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 响应结果封装
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务响应码")
    private Integer code;

    @ApiModelProperty(value = "响应信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    private Result() {}

    /**
     * 返回成功但不带数据的结果
     * @return 返回结果封装
     */
    public static Result ok() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    /**
     * 返回失败的结果
     * @return 返回结果封装
     */
    public static Result error() {
        Result result = new Result();
        result.setCode(ResultCode.COMMON_ERROR.getCode());
        result.setMessage(ResultCode.COMMON_ERROR.getMessage());
        return result;
    }

    /**
     * 返回特定的结果
     * @param resultCode 自定义错误码
     * @return 返回结果封装
     */
    public static Result error(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    /**
     * 设置特定的响应数据
     * @param key 数据的键
     * @param value 数据的值
     * @return 返回结果封装
     */
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 设置特定的响应信息
     * @param message 响应信息
     * @return 返回结果封装
     */
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置特定的响应码
     * @param code 响应码
     * @return 返回结果封装
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }
}
