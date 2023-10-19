package com.jshy.apis.wemedia.fallback;

import com.jshy.apis.article.IArticleClient;
import com.jshy.apis.schedule.IScheduleClient;
import com.jshy.apis.wemedia.IWemediaClient;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdChannelUpDateDto;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.schedule.dtos.Task;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign失败配置
 *
 * @author itjshy
 */
@Component
public class IWemediaClientFallback implements IWemediaClient {
    @Override
    public ResponseResult findChannel(AdChannelDto adChannelDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult updateChannel(AdChannelUpDateDto adChannelUpDateDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult delChannelById(Integer id) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult saveChannel(AdChannelUpDateDto adChannelUpDateDto) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }

    @Override
    public ResponseResult getChannels() {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "获取数据失败");
    }
}