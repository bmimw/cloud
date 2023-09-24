package com.jshy.model.commodity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class CmRecommendDetailsDto {
    /**
     * 活动标题
     */
    @ApiModelProperty(value = "活动标题", required = true)
    private String title;
    /**
     * id信息
     */
    @ApiModelProperty(value = "id信息", required = true)
    private String id;
    /**
     * 活动图片
     */
    @ApiModelProperty(value = "活动图片", required = true)
    private String bannerPicture;
    /**
     * 子选项集合
     */
    @ApiModelProperty(value = "子选项集合", required = true)
    private List<SubType> subTypes;

    @Data
    public static class SubType {
        /**
         * 子类选项id
         */
        @ApiModelProperty(value = "子类选项id", required = true)
        private String id;
        /**
         * 子类选项名称
         */
        @ApiModelProperty(value = "子类选项名称", required = true)
        private String title;
        /**
         * 分页商品属性
         */
        @ApiModelProperty(value = "分页商品属性", required = true)
        private GoodsItems goodsItems;

        @Data
        public static class GoodsItems {
            /**
             * 总条数
             */
            @ApiModelProperty(value = "总条数", required = true)
            private Integer counts;
            /**
             * 每页条数
             */
            @ApiModelProperty(value = "每页条数", required = true)
            private Integer pageSize;
            /**
             * 总页数
             */
            @ApiModelProperty(value = "总页数", required = true)
            private Integer pages;
            /**
             * 当前页数
             */
            @ApiModelProperty(value = "当前页数", required = true)
            private Integer page;
            /**
             * 当前页数据
             */
            @ApiModelProperty(value = "当前页数据", required = true)
            private List<Items> items;

            @Data
            public static class Items {
                /**
                 * 主键id
                 */
                @ApiModelProperty(value = "主键id", required = true)
                private Integer id;
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
                private Integer price;
                /**
                 * 商品图片
                 */
                @ApiModelProperty(value = "商品图片", required = true)
                private String picture;
            }
        }
    }
}

