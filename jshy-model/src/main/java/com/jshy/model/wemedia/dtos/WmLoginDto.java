package com.jshy.model.wemedia.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WmLoginDto {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String name;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
