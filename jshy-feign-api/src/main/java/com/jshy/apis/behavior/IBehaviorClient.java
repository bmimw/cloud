package com.jshy.apis.behavior;

import com.jshy.apis.behavior.fallback.IBehaviorClientFallback;
import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.behavior.dtos.UnLikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "jshy-behavior", fallback = IBehaviorClientFallback.class)
public interface IBehaviorClient {
    /**
     * 用户点赞
     */
    @PostMapping("/api/v1/likes_behavior")
    public ResponseResult likesBehavior(@RequestBody LikesBehaviorDto likesBehaviorDto, HttpServletRequest request);

    /**
     * 用户阅读
     */
    @PostMapping("/api/v1/read_behavior")
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto readBehaviorDto,HttpServletRequest request);

    /**
     * 不喜欢
     */
    @PostMapping("/api/v1/un_likes_behavior")
    public ResponseResult unLikesBehavior(@RequestBody UnLikesBehaviorDto likesBehaviorDto,HttpServletRequest request);

}
