package com.jshy.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmRecommendMapper;
import com.jshy.commodity.service.CmGoodsService;
import com.jshy.commodity.service.CmRecommendOptionService;
import com.jshy.commodity.service.CmRecommendService;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.dtos.CmRecommendDto;
import com.jshy.model.commodity.pojos.CmRecommend;
import com.jshy.model.commodity.pojos.CmRecommendOption;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CmRecommendServiceImpl extends ServiceImpl<CmRecommendMapper, CmRecommend> implements CmRecommendService {
    /**
     * 首页获取热门推荐
     */
    @Override
    public ResponseResult findIndexRecommend() {
        //先查询出来需要的数据
        List<CmRecommend> cmRecommends = list(Wrappers.<CmRecommend>lambdaQuery().eq(CmRecommend::getTarget, 10050));
        //创建需要的数据传输
        List<CmRecommendDto> cmRecommendDtos = new ArrayList<>();
        // 进行数据的拷贝并处理图像集合属性
        for (CmRecommend cmRecommend : cmRecommends) {
            CmRecommendDto dto = new CmRecommendDto();
            // 使用 BeanUtils 复制属性
            BeanUtils.copyProperties(cmRecommend, dto);
            // 处理图像集合属性，将逗号分隔的字符串拆分成字符串数组
            if (cmRecommend.getPictures() != null) {
                dto.setPictures(cmRecommend.getPictures().split(","));
            }
            // 将处理后的 DTO 添加到列表
            cmRecommendDtos.add(dto);
        }
        return ResponseResult.okResult(cmRecommendDtos);
    }

    @Autowired
    CmRecommendOptionService cmRecommendOptionService;

    @Autowired
    CmGoodsService cmGoodsService;

    /**
     * 首页热门推荐详情
     */
    @Override
    public ResponseResult findIndexRecommendDetails(String type) {
        //先检查参数
        if (type.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "无效参数");
        }
        //进行查询对应的推荐信息
        CmRecommendDetailsDto cmRecommendDetailsDto = new CmRecommendDetailsDto();
        CmRecommend cmRecommend = getOne(Wrappers.<CmRecommend>lambdaQuery().eq(CmRecommend::getType, type));
        if (cmRecommend == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "数据不存在");
        }
        BeanUtils.copyProperties(cmRecommend, cmRecommendDetailsDto);

        //添加对应的推荐选项
        cmRecommendDetailsDto = cmRecommendOptionService.findTitleByTypeId(type, cmRecommendDetailsDto);
        //添加推荐商品
        cmRecommendDetailsDto = cmGoodsService.getRecommendGoods(cmRecommendDetailsDto);
        return ResponseResult.okResult(cmRecommendDetailsDto);
    }
}