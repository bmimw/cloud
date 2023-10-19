package com.jshy.behavior.feign;

import com.jshy.apis.behavior.IBehaviorClient;
import com.jshy.behavior.service.ApLikesBehaviorService;
import com.jshy.behavior.service.BehaviorService;
import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.behavior.dtos.UnLikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BehaviorClient implements IBehaviorClient {

    @Autowired
    BehaviorService behaviorService;

    @Autowired
    ApLikesBehaviorService apLikesBehaviorService;

    /**
     * 用户点赞
     */
    @Override
    public ResponseResult likesBehavior(@RequestBody LikesBehaviorDto likesBehaviorDto, HttpServletRequest request) {
        return apLikesBehaviorService.likesBehavior(likesBehaviorDto,request);
    }

    /**
     * 用户阅读
     */
    @Override
    public ResponseResult readBehavior(@RequestBody ReadBehaviorDto readBehaviorDto,HttpServletRequest request) {
        return behaviorService.readBehavior(readBehaviorDto,request);
    }

    /**
     * 不喜欢
     */
    @Override
    public ResponseResult unLikesBehavior(@RequestBody UnLikesBehaviorDto likesBehaviorDto,HttpServletRequest request) {
        return behaviorService.unLikesBehavior(likesBehaviorDto,request);
    }

}
