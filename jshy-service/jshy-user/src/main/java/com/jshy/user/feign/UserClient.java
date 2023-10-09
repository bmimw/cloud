package com.jshy.user.feign;

import com.jshy.apis.user.IUserClient;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClient implements IUserClient {
    @Autowired
    ApUserRealnameService apUserRealnameService;
    /**
     * 分页条件查询用户
     * */
    public ResponseResult findUserList(AdUserDto adUserDto) {
        return apUserRealnameService.findUserList(adUserDto);
    }
    /**
     * 审核成功
     * */
    @Override
    public ResponseResult authPass(AdUserDto adUserDto) {
        return apUserRealnameService.authPass(adUserDto);
    }
    /**
     * 审核失败
     * */
    @Override
    public ResponseResult authFail(AdUserDto adUserDto) {
        return apUserRealnameService.authFail(adUserDto);
    }
}
