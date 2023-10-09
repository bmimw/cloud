package com.jshy.model.admin.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdLoginDto {

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
