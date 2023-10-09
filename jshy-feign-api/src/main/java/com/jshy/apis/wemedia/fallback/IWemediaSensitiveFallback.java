package com.jshy.apis.wemedia.fallback;

import com.jshy.apis.wemedia.IWemediaSensitive;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdSensitiveDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

/**
 * feign失败配置
 *
 * @author itjshy
 */
@Component
public class IWemediaSensitiveFallback implements IWemediaSensitive {

    @Override
    public ResponseResult list(AdChannelDto adChannelDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
    @Override
    public ResponseResult del(Integer id) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult save(AdSensitiveDto adSensitiveDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult update(AdSensitiveDto adSensitiveDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}
