package org.rhett.mysecurity.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rhett.mysecurity.common.ResultCode;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * 自定义异常
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    @ApiModelProperty(value = "错误码")
    private Integer code;
    @ApiModelProperty(value = "错误信息")
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public BusinessException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
