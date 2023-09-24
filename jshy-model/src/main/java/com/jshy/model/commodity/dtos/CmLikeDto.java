package com.jshy.model.commodity.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CmLikeDto {
    /**
     * 总条数
     * */
    @ApiModelProperty(value = "总条数",required = true)
    private Integer counts;
    /**
     * 每页条数
     * */
    @ApiModelProperty(value = "每页条数",required = true)
    private Integer pageSize;
    /**
     * 总页数
     * */
    @ApiModelProperty(value = "总页数",required = true)
    private Integer pages;
    /**
     * 当前页数
     * */
    @ApiModelProperty(value = "当前页数",required = true)
    private Integer page;
    /**
     * 当前页数据
     * */
    @ApiModelProperty(value = "当前页数据",required = true)
    private List<CmLikeGoodDto> items;
}
