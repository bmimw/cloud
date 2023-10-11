package com.jshy.model.article.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CollectionBehaviorDto {
    @ApiModelProperty(value = "文章id",required = false)
    private Long entryId;
    @ApiModelProperty(value = "0收藏    1取消收藏",required = false)
    private Short operation;
    @ApiModelProperty(value = "发布时间",required = false)
    private Date publishedTime;
    @ApiModelProperty(value = "0文章    1动态",required = false)
    private Short type;
}
