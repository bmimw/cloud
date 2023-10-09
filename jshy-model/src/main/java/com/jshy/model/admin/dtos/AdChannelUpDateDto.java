package com.jshy.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdChannelUpDateDto {
    /**
     * 频道的详细描述
     */
    @ApiModelProperty(value = "频道的详细描述",required = false)
    private String description;
    /**
     * 频道的id
     */
    @ApiModelProperty(value = "频道的id",required = false)
    private Integer id;
    /**
     * 频道的名称
     */
    @ApiModelProperty(value = "频道的名称",required = false)
    private String name;
    /**
     * 频道的默认排序
     */
    @ApiModelProperty(value = "频道的默认排序",required = false)
    private Integer ord;
    /**
     * 频道的状态
     */
    @ApiModelProperty(value = "频道的状态",required = true)
    private Boolean status;
}
