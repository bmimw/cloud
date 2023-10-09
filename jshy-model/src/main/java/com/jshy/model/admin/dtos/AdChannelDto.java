package com.jshy.model.admin.dtos;

import com.jshy.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdChannelDto extends PageRequestDto {
    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段",required = false)
    private String name;
}
