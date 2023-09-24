package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.pojos.CmRecommendOption;
import com.jshy.model.common.dtos.ResponseResult;

public interface CmRecommendOptionService extends IService<CmRecommendOption> {
    /**
     * 根据类型id获取对应的选项
     * */
    public CmRecommendDetailsDto findTitleByTypeId(String Type ,CmRecommendDetailsDto cmRecommendDetailsDto);
}
