package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmCreatedDto;
import com.jshy.model.wemedia.dtos.WmLoginDto;
import com.jshy.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {

    /**
     * 商家端口登录
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);

}