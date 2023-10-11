package com.jshy.model.behavior.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReadBehaviorDto {

    @ApiModelProperty(value = "文章id", required = false)
    private Long articleId;
    @ApiModelProperty(value = "阅读次数", required = false)
    private Integer count;
}
