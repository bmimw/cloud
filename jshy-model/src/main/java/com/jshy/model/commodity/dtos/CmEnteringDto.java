package com.jshy.model.commodity.dtos;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CmEnteringDto {
    /**
     *  商品名称
     * */
    @ApiModelProperty(value = "商品名称",required = true)
    private String name;
    /**
     *  商品描述
     * */
    @ApiModelProperty(value = "商品描述",required = true)
    private String desc;
    /**
     *  商品价格
     * */
    @ApiModelProperty(value = "商品价格",required = true)
    private String price;
    /**
     *  商品类型id
     * */
    @ApiModelProperty(value = "商品类型",required = true)
    private long type;
    /**
     *  商品总库存数
     * */
    @ApiModelProperty(value = "商品总库存数",required = true)
    private String inventory;
}
