package org.rhett.mysecurity.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "成功"),
    COMMON_ERROR(9999, "错误");

    @ApiModelProperty(value = "错误码")
    private Integer code;

    @ApiModelProperty(value = "错误码")
    private String message;
}
