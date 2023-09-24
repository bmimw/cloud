package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.pojos.CmRecommend;
import com.jshy.model.common.dtos.ResponseResult;

public interface CmRecommendService extends IService<CmRecommend> {
    /**
     * 首页获取热门推荐
     * */
    public ResponseResult findIndexRecommend();
    /**
     * 首页热门推荐详情
     * */
    public ResponseResult findIndexRecommendDetails(String type);
}
