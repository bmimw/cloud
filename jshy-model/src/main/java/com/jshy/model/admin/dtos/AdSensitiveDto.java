package com.jshy.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdSensitiveDto {
    /**
     * id
     */
    @ApiModelProperty(value = "id",required = false)
    private Integer id;

    /**
     * 敏感词
     */
    @ApiModelProperty(value = "敏感词",required = true)
    private String sensitives;
}
