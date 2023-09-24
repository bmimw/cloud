package com.jshy.commodity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmParentMapper;
import com.jshy.commodity.service.CmParentService;
import com.jshy.model.commodity.pojos.CmParent;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
