package com.jshy.wemedia.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.wemedia.pojos.WmNewsMaterial;
import com.jshy.wemedia.mapper.WmNewsMaterialMapper;
import com.jshy.wemedia.service.WmNewsMaterialSercice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class WmNewsMaterialSerciceImpl extends ServiceImpl<WmNewsMaterialMapper, WmNewsMaterial> implements WmNewsMaterialSercice {

    /**
     *  查询素材id所对应的所有文章
     * @param id
     */
    @Override
    public ResponseResult findAllNewMaterial(Integer id){
        //1.校验参数
        if (id.equals("")){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"参数不可为空");
        }
        //进行查询所有
        List<WmNewsMaterial> wmNewsMaterials = list(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getMaterialId, id));
        //返回
        return ResponseResult.okResult(wmNewsMaterials);
    }
}
