package com.jshy.apis.user;

import com.jshy.apis.user.fallback.IUserClientFallback;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.admin.pojos.AdUser;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jshy-user",fallback = IUserClientFallback.class)
public interface IUserClient {

    /**
     * 条件查询
     * */
    @PostMapping("/api/v1/auth/list")
    public ResponseResult findUserList(@RequestBody AdUserDto adUserDto);

    /**
     * 审核成功
     * */
    @PostMapping("/api/v1/auth/authPass")
    public ResponseResult authPass(@RequestBody AdUserDto adUserDto);

    /**
     * 审核失败
     * */
    @PostMapping("/api/v1/auth/authFail")
    public ResponseResult authFail(@RequestBody AdUserDto adUserDto);

}
