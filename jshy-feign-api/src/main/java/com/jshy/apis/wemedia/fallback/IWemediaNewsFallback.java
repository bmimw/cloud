package com.jshy.apis.wemedia.fallback;

import com.jshy.apis.wemedia.IWemediaNews;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.admin.dtos.AdWemediaNewsDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

/**
 * feign失败配置
 *
 * @author itjshy
 */
@Component
public class IWemediaNewsFallback implements IWemediaNews {
    /**
     * 分页条件查询文章
     */
    @Override
    public ResponseResult findListVo(AdWemediaNewsDto adWemediaNewsDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult findOneVo(Integer id) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult authFail(AdWemediaNewsDto adWemediaNewsDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult authPass(AdWemediaNewsDto adWemediaNewsDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}
