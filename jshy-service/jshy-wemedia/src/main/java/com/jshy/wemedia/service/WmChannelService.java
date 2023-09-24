package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     * @return
     */
    public ResponseResult findAll();


}