package com.jshy.model.commodity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CmParentDto {
    /**
     * 一级分类id
     */
    @ApiModelProperty(value = "一级分类id", required = true)
    private String id;

    /**
     * 一级分类名称
     */
    @ApiModelProperty(value = "一级分类名称", required = true)
    private String name;

    /**
     * 二级分类集合
     */
    @ApiModelProperty(value = "二级分类集合\n", required = true)
    private List<Children> children;

    @Data
    public static class Children {

        /**
         * 二级分类id
         */
        @ApiModelProperty(value = "二级分类id", required = true)
        private String id;

        /**
         * 二级分类名称
         */
        @ApiModelProperty(value = "二级分类名称", required = true)
        private String name;

        @ApiModelProperty(value = "商品集合",required = true)
        private List<Goods> goods;

        @Data
        public static class Goods{

            /**
             * 商品id
             */
            @ApiModelProperty(value = "商品id", required = true)
            private String id;

            /**
             * 商品名称
             */
            @ApiModelProperty(value = "商品名称", required = true)
            private String name;

            /**
             * 商品描述
             */
            @ApiModelProperty(value = "商品描述", required = true)
            private String desc;

            /**
             * 商品价格
             */
            @ApiModelProperty(value = "商品价格", required = true)
            private String price;

            /**
             * 商品图片
             */
            @ApiModelProperty(value = "商品图片", required = true)
            private String picture;
        }
    }
}
