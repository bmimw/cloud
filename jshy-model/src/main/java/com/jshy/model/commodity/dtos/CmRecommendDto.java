package com.jshy.model.commodity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CmRecommendDto {
    private Integer id;
    /**
     *  推荐说明
     */
    @ApiModelProperty(value = "推荐说明",required = true)
    private String alt;
    /**
     *  图片合集
     */
    @ApiModelProperty(value = "图片合集",required = true)
    private String[] pictures;
    /**
     *  跳转地址
     */
    @ApiModelProperty(value = "跳转地址",required = true)
    private String target;
    /**
     *  推荐标题
     */
    @ApiModelProperty(value = "推荐标题",required = true)
    private String title;
    /**
     *  推荐类型
     */
    @ApiModelProperty(value = "推荐类型",required = true)
    private String type;
}
