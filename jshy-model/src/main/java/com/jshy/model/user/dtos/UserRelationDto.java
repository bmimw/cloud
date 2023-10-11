package com.jshy.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRelationDto {
    @ApiModelProperty(value = "文章id",required = false)
    private Long articleId;
    @ApiModelProperty(value = "作者id",required = false)
    private Integer authorId;
    @ApiModelProperty(value = "0  关注   1  取消",required = false)
    private Short operation;
}
