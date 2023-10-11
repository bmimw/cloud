package com.jshy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.user.dtos.LoginDto;
import com.jshy.model.user.dtos.RegisterDto;
import com.jshy.model.user.dtos.UserRelationDto;
import com.jshy.model.user.pojos.ApUser;

import javax.servlet.http.HttpServletRequest;

public interface ApUserService extends IService<ApUser> {

    /**
     * app端登录
     *
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

    /**
     * app端注册
     *
     * @param dto
     * @return
     */
    public ResponseResult register(RegisterDto dto);

    /**
     * 用户关注、取消关注
     */
    public ResponseResult userFollow(HttpServletRequest request,UserRelationDto dto);

}