package com.jshy.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdLoginDto;
import com.jshy.model.admin.pojos.AdUser;
import com.jshy.model.common.dtos.ResponseResult;

public interface AdUserService extends IService<AdUser> {
    /**
     * 商家端口登录
     * @param dto
     * @return
     */
    public ResponseResult login(AdLoginDto dto);
}
