package com.jshy.apis.user.fallback;

import com.jshy.apis.user.IUserClient;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;

public class IUserClientFallback implements IUserClient {
    @Override
    public ResponseResult findUserList(AdUserDto adUserDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult authPass(AdUserDto adUserDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult authFail(AdUserDto adUserDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}
