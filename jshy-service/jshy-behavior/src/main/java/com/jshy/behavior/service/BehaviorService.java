package com.jshy.behavior.service;

import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.behavior.dtos.UnLikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface BehaviorService {


    /**
     * 不喜欢
     */
    ResponseResult unLikesBehavior(UnLikesBehaviorDto likesBehaviorDto,HttpServletRequest request);
}
