package com.jshy.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterDto {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;

    /**
     * 密码,md5加密
     */
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = false)
    private String image;

    /**
     * 0 男
     1 女
     2 未知
     */
    @ApiModelProperty(value = "性别",required = false)
    private Integer sex;
}
