package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.pojos.CmGoods;
import com.jshy.model.common.dtos.ResponseResult;

public interface CmGoodsService extends IService<CmGoods> {

    /**
     * 获取首页“猜你喜欢”
     * */
    public ResponseResult getGuessLike(Integer page, Integer pageSize);

    /**
     * 获取对应的推荐商品
     * */
    public CmRecommendDetailsDto getRecommendGoods(CmRecommendDetailsDto cmRecommendDetailsDto);
}
