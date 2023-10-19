package com.jshy.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.jshy.behavior.service.ApLikesBehaviorService;
import com.jshy.common.constants.BehaviorConstants;
import com.jshy.common.constants.HotArticleConstants;
import com.jshy.common.redis.CacheService;
import com.jshy.model.behavior.dtos.LikesBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.mess.UpdateArticleMess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class ApLikesBehaviorServiceImpl implements ApLikesBehaviorService {

    @Autowired
    CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    /**
     * 用户点赞
     */
    @Override
    public ResponseResult likesBehavior(LikesBehaviorDto likesBehaviorDto, HttpServletRequest request) {
//        //文章校验
//        if (!articleClient.findApArticleById(likesBehaviorDto.getArticleId())){
//            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
//        }

        //1.检查参数
        if (likesBehaviorDto == null || likesBehaviorDto.getArticleId() == null || checkParam(likesBehaviorDto)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.是否登录
        //得到header中的信息
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(likesBehaviorDto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.LIKES);

//        //2.是否登录
//        ApUser user = AppThreadLocalUtil.getUser();
//        if (user == null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }
//
//        String keyUser = userId + "_like_" + likesBehaviorDto.getType();
//        String key = likesBehaviorDto.getArticleId() + "_beLike_" + likesBehaviorDto.getType();
//
//        if (likesBehaviorDto.getOperation()==0){
//            //点赞
//            //用户主键redis添加信息
//            cacheService.sAdd(keyUser,String.valueOf(likesBehaviorDto.getArticleId()));
//            //文章主键redis添加信息
//            cacheService.sAdd(key,userId);
//            return ResponseResult.okResult(null);
//
//        }else if (likesBehaviorDto.getOperation()==1){
//            //取消点赞
//            //用户主键redis删除信息
//            cacheService.sRemove(keyUser,String.valueOf(likesBehaviorDto.getArticleId()));
//            //文章主键redis删除信息
//            cacheService.sRemove(key,userId);
//            return ResponseResult.okResult(null);
//        }


        //3.点赞  保存数据
        if (likesBehaviorDto.getOperation() == 0) {
            Object obj = cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(), userId);
            if (obj != null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "已点赞");
            }
            // 保存当前key
            log.info("保存当前key:{} ,{}, {}", likesBehaviorDto.getArticleId(), userId, likesBehaviorDto);
            cacheService.hPut(BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(), userId, JSON.toJSONString(new Date()));
            mess.setAdd(1);
        } else {
            // 删除当前key
            log.info("删除当前key:{}, {}", likesBehaviorDto.getArticleId(), userId);
            cacheService.hDelete(BehaviorConstants.LIKE_BEHAVIOR + likesBehaviorDto.getArticleId().toString(), userId);
            mess.setAdd(-1);
        }

        //发送消息，数据聚合
        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(mess));

        return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
    }


    /**
     * 检查参数
     *
     * @return
     */
    private boolean checkParam(LikesBehaviorDto dto) {
        if (dto.getType() > 2 || dto.getType() < 0 || dto.getOperation() > 1 || dto.getOperation() < 0) {
            return true;
        }
        return false;
    }
}
