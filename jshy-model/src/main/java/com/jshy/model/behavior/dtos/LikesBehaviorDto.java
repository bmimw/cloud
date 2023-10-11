package com.jshy.model.behavior.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LikesBehaviorDto {

    @ApiModelProperty(value = "文章id",required = false)
    private Long articleId;
    @ApiModelProperty(value = "0 点赞   1 取消点赞",required = false)
    private Short operation;
    @ApiModelProperty(value = "0文章  1动态   2评论",required = false)
    private Short type;

}
