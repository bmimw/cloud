package com.jshy.apis.behavior.fallback;

import com.jshy.apis.behavior.IBehaviorClient;
import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.behavior.dtos.UnLikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * feign失败配置
 * @author itjshy
 */
@Component
public class IBehaviorClientFallback implements IBehaviorClient {

    @Override
    public ResponseResult likesBehavior(LikesBehaviorDto likesBehaviorDto, HttpServletRequest request) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }

    @Override
    public ResponseResult readBehavior(ReadBehaviorDto readBehaviorDto,HttpServletRequest request) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }

    @Override
    public ResponseResult unLikesBehavior(UnLikesBehaviorDto likesBehaviorDto,HttpServletRequest request) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }
}
