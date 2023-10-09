package com.jshy.model.admin.dtos;

import com.jshy.model.common.dtos.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdWemediaNewsDto extends PageRequestDto {
    /**
     * 文章id
     */
    @ApiModelProperty(value = "id",required = false)
    private Integer id;
    /**
     * 驳回原因
     */
    @ApiModelProperty(value = "驳回原因",required = false)
    private String msg;
    /**
     *文章状态
     */
    @ApiModelProperty(value = "文章状态",required = false)
    private Integer status;
    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题",required = false)
    private String title;
}
