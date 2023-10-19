package com.jshy.behavior.service;

import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface ApLikesBehaviorService {
    /**
     * 用户点赞
     */
    ResponseResult likesBehavior(LikesBehaviorDto likesBehaviorDto, HttpServletRequest request);
}
