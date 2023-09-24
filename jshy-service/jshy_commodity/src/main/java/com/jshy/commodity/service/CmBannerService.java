package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.pojos.CmBanner;
import com.jshy.model.common.dtos.ResponseResult;

public interface CmBannerService extends IService<CmBanner> {
    /**
     * 轮播图输出
     * */
    public ResponseResult findBannerType(Integer type);
}
