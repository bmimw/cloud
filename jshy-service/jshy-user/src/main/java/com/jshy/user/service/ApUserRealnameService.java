package com.jshy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.user.pojos.ApUserRealname;

public interface ApUserRealnameService extends IService<ApUserRealname> {
    /**
     * 分页条件查询用户
     * */
    public ResponseResult findUserList(AdUserDto adUserDto);

    /**
     * 审核成功
     */
    public ResponseResult authPass(AdUserDto adUserDto);
    /**
     * 审核失败
     * */
    public ResponseResult authFail(AdUserDto adUserDto);
}
