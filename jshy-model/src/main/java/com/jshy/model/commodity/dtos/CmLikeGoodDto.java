package com.jshy.model.commodity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CmLikeGoodDto {
    /**
     * 主键id
     * */
    @ApiModelProperty(value = "主键id",required = true)
    private Integer id;
    /**
     * 商品名称
     * */
    @ApiModelProperty(value = "商品名称",required = true)
    private String name;
    /**
     * 商品描述
     * */
    @ApiModelProperty(value = "商品描述",required = true)
    private String desc;
    /**
     * 商品价格
     * */
    @ApiModelProperty(value = "商品价格",required = true)
    private Integer price;
    /**
     * 商品图片
     * */
    @ApiModelProperty(value = "商品图片",required = true)
    private String picture;
    /**
     * 商品折扣
     * */
    @ApiModelProperty(value = "商品折扣",required = true)
    private Integer discount;
    /**
     * 商品已下单数
     * */
    @ApiModelProperty(value = "商品已下单数",required = true)
    private Integer orderNum;

}
