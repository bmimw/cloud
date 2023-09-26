package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.dtos.CmParentDto;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.pojos.CmGoods;
import com.jshy.model.common.dtos.ResponseResult;

import java.util.List;

public interface CmGoodsService extends IService<CmGoods> {

    /**
     * 获取首页“猜你喜欢”
     * */
    public ResponseResult getGuessLike(Integer page, Integer pageSize);

    /**
     * 获取对应的推荐商品
     * */
    public CmRecommendDetailsDto getRecommendGoods(CmRecommendDetailsDto cmRecommendDetailsDto);

    /**
     * 根据分类二级id进行查找对应的商品
     * */
    public List<CmParentDto.Children.Goods> getParentGoods(Integer typeId);
}
