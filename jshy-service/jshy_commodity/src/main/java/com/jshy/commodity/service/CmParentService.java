package com.jshy.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.commodity.pojos.CmParent;
import com.jshy.model.common.dtos.ResponseResult;

public interface CmParentService extends IService<CmParent> {
    /**
     * 获取首页分类
     * */
    public ResponseResult findIndexParent();
}
