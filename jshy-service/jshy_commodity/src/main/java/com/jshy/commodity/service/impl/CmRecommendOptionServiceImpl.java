package com.jshy.commodity.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmRecommendOptionMapper;
import com.jshy.commodity.service.CmGoodsService;
import com.jshy.commodity.service.CmRecommendOptionService;
import com.jshy.model.commodity.dtos.CmRecommendDetailsDto;
import com.jshy.model.commodity.pojos.CmRecommendOption;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmRecommendOptionServiceImpl extends ServiceImpl<CmRecommendOptionMapper, CmRecommendOption> implements CmRecommendOptionService {
    /**
     * 根据类型id查询推荐模块的选项
     */
    @Override
    public CmRecommendDetailsDto findTitleByTypeId(String Type, CmRecommendDetailsDto cmRecommendDetailsDto) {
        //查询对应的推荐选项信息
        CmRecommendOption cmRecommendOption = getOne(Wrappers.<CmRecommendOption>lambdaQuery().eq(CmRecommendOption::getTypeId, Type));
        if (cmRecommendOption == null) {
             throw new RuntimeException("查询不到对应的信息");
        }
        //取对应的选项id以及选项名称
        cmRecommendDetailsDto=getTypeId(cmRecommendOption.getTitle(),cmRecommendDetailsDto);
        return cmRecommendDetailsDto;
    }
    /**
     * 提取对应的选项id以及选项名称
     * */
    private CmRecommendDetailsDto getTypeId(String title,CmRecommendDetailsDto cmRecommendDetailsDto){
        List<CmRecommendDetailsDto.SubType> subTypes=new ArrayList<>();
        List<Map> maps = JSON.parseArray(title, Map.class);
        for (Map map : maps) {
            if(map.get("type").equals("type")){
                String str = (String) map.get("value");
                String[] string = str.split(",");
                //添加对应的信息到CmRecommendDetailsDto
                CmRecommendDetailsDto.SubType subType= new CmRecommendDetailsDto.SubType();
                subType.setId(string[1]);
                subType.setTitle(string[0]);
                subTypes.add(subType);
            }
        }
        cmRecommendDetailsDto.setSubTypes(subTypes);
        return cmRecommendDetailsDto;
    }
}