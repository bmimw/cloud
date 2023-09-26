package com.jshy.commodity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmParentMapper;
import com.jshy.commodity.service.CmGoodsService;
import com.jshy.commodity.service.CmParentService;
import com.jshy.model.commodity.dtos.CmParentDto;
import com.jshy.model.commodity.pojos.CmParent;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CmParentServiceImpl extends ServiceImpl<CmParentMapper, CmParent> implements CmParentService {
    /**
     * 查询首页的分类
     */
    @Override
    public ResponseResult findIndexParent() {
        return findParent(1);
    }

    @Autowired
    CmGoodsService cmGoodsService;

    @Override
    public ResponseResult findCategoryParent() {
        //创建dto
        List<CmParentDto> cmParentDtos=new ArrayList<>();
        //先查询所有的一级分类
        List<CmParent> cmParents=list(Wrappers.<CmParent>lambdaQuery().eq(CmParent::getLayer,1));
        for (CmParent cmParent:cmParents){
            //添加一级分类所需要的信息
            CmParentDto cmParentDto=new CmParentDto();
            cmParentDto.setId(String.valueOf(cmParent.getId()));
            cmParentDto.setName(cmParent.getName());

            List<CmParentDto.Children> children=new ArrayList<>();
            //遍历一级分类添加二级分类
            List<CmParent> cmParents1=list((Wrappers.<CmParent>lambdaQuery().eq(CmParent::getParentId,cmParent.getId())));
            for (CmParent cmParent1:cmParents1){
                //创建,添加二级分类信息
                CmParentDto.Children children1=new CmParentDto.Children();
                children1.setId(String.valueOf(cmParent1.getId()));
                children1.setName(cmParent1.getName());
                //遍历二级分类添加商品
                children1.setGoods(cmGoodsService.getParentGoods(cmParent1.getId()));
                children.add(children1);
            }
            //补全信息
            cmParentDto.setChildren(children);
            //添加上去
            cmParentDtos.add(cmParentDto);
        }
        return ResponseResult.okResult(cmParentDtos);
    }

    private ResponseResult findParent(Integer layer) {
        //参数判断
        if (layer == null && layer == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        List<CmParent> parentList = list(Wrappers.<CmParent>lambdaQuery().eq(CmParent::getLayer, layer));
        //值检查
        if (parentList == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        } else {
            return ResponseResult.okResult(parentList);
        }
    }
}
