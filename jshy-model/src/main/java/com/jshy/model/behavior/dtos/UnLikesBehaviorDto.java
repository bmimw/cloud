package com.jshy.model.behavior.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnLikesBehaviorDto {

    @ApiModelProperty(value = "Id文章id",required = false)
    private Long articleId;
    @ApiModelProperty(value = "0 不喜欢      1 取消不喜欢",required = false)
    private Short type;
}
