package com.jshy.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.jshy.behavior.service.BehaviorService;
import com.jshy.common.redis.CacheService;
import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.behavior.dtos.UnLikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@Transactional
public class BehaviorServiceImpl implements BehaviorService {
    @Autowired
    CacheService cacheService;
    /**
     * 用户点赞
     */
    @Override
    public ResponseResult likesBehavior(LikesBehaviorDto likesBehaviorDto, HttpServletRequest request) {
//        //文章校验
//        if (!articleClient.findApArticleById(likesBehaviorDto.getArticleId())){
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
//        }
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

//        //获取当前用户
//        ApUser user = AppThreadLocalUtils.getUser();
//        if(user == null){
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }

        String keyUser = userId + "_like_" + likesBehaviorDto.getType();
        String key = likesBehaviorDto.getArticleId() + "_beLike_" + likesBehaviorDto.getType();

        if (likesBehaviorDto.getOperation()==0){
            //点赞
            //用户主键redis添加信息
            cacheService.sAdd(keyUser,String.valueOf(likesBehaviorDto.getArticleId()));
            //文章主键redis添加信息
            cacheService.sAdd(key,userId);
            return ResponseResult.okResult(null);

        }else if (likesBehaviorDto.getOperation()==1){
            //取消点赞
            //用户主键redis删除信息
            cacheService.sRemove(keyUser,String.valueOf(likesBehaviorDto.getArticleId()));
            //文章主键redis删除信息
            cacheService.sRemove(key,userId);
            return ResponseResult.okResult(null);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }

    /**
     * 用户阅读
     */
    @Override
    public ResponseResult readBehavior(ReadBehaviorDto readBehaviorDto,HttpServletRequest request) {
//        //文章校验
//        if (!articleClient.findApArticleById(likesBehaviorDto.getArticleId())){
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
//        }
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
//        //获取当前用户
//        ApUser user = AppThreadLocalUtils.getUser();
//        if(user == null){
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }
        String key = userId + "_readArticle" + readBehaviorDto.getArticleId();

        if (cacheService.sSize(key)==null){
            //用户主键redis添加信息
            cacheService.sAdd(key,JSON.toJSONString(readBehaviorDto.getCount()));
            return ResponseResult.okResult(null);
        }else {
            cacheService.sRemove(key,JSON.toJSONString(readBehaviorDto.getCount()-1));
            cacheService.sAdd(key,JSON.toJSONString(readBehaviorDto.getCount()));
            return ResponseResult.okResult(null);
        }
    }

    /**
     * 不喜欢
     */
    @Override
    public ResponseResult unLikesBehavior(UnLikesBehaviorDto likesBehaviorDto,HttpServletRequest request) {
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        String key=userId+"_dislike";

        if (likesBehaviorDto.getType()==0){
            //不喜欢
            //用户主键redis添加信息
            cacheService.sAdd(key,String.valueOf(likesBehaviorDto.getArticleId()));
            return ResponseResult.okResult(null);
        }else if (likesBehaviorDto.getType()==1){
            //取消不喜欢
            cacheService.sRemove(key,String.valueOf(likesBehaviorDto.getArticleId()));
            return ResponseResult.okResult(null);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }
}
