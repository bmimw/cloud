package com.jshy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.user.dtos.LoginDto;
import com.jshy.model.user.dtos.RegisterDto;
import com.jshy.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser>{

    /**
     * app端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
    /**
     * app端注册
     * @param dto
     * @return
     */
    public ResponseResult register(RegisterDto dto);
}