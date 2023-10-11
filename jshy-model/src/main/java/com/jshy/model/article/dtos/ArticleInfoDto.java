package com.jshy.model.article.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleInfoDto {
    @ApiModelProperty(value = "文章id",required = false)
    private Long articleId;
    @ApiModelProperty(value = "作者id",required = false)
    private Integer authorId;
}
