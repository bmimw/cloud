package com.jshy.model.wemedia.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WmCreatedDto {
    /**
     *  对应的app端口账号id
     * */
    @ApiModelProperty(value = "对应的app端口账号id",required = false)
    private Integer ap_user_id;
    /**
     *  用来登陆的用户名（账号）
     * */
    @ApiModelProperty(value = "账号",required = true)
    private String name;
    /**
     *  密码
     * */
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    /**
     *  用来显示的用户名
     * */
    @ApiModelProperty(value = "用户名",required = true)
    private String nickname;
    /**
     *  手机号
     * */
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;
    /**
     *  性别
     * */
    @ApiModelProperty(value = "性别",required = false)
    private Integer status;

}
