package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.pojos.WmNewsMaterial;

public interface WmNewsMaterialSercice extends IService<WmNewsMaterial> {

    /**
     *  查询素材id所对应的所有文章
     * @param id
     * @return
     */
    public ResponseResult findAllNewMaterial(Integer id);

}
