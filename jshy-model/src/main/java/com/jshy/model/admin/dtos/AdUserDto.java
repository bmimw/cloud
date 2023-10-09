package com.jshy.model.admin.dtos;

import com.jshy.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdUserDto extends PageRequestDto {
    /**
     * 用户的id
     */
    @ApiModelProperty(value = "用户的id", required = false)
    private Integer id;

    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "msg", required = false)
    private String msg;

    /**
     * 用户当前的状态
     */
    @ApiModelProperty(value = "用户的当前状态", required = false)
    private Integer status;
}
