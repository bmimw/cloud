package com.jshy.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import com.jshy.behavior.service.ApReadBehaviorService;
import com.jshy.common.constants.BehaviorConstants;
import com.jshy.common.constants.HotArticleConstants;
import com.jshy.common.redis.CacheService;
import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.mess.UpdateArticleMess;
import com.jshy.model.user.pojos.ApUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class ApReadBehaviorServiceImpl implements ApReadBehaviorService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public ResponseResult readBehavior(ReadBehaviorDto dto, HttpServletRequest request) {
        //1.检查参数
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //2.是否登录
//        ApUser user = AppThreadLocalUtil.getUser();
//        if (user == null) {
//            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
//        }
        String userId = request.getHeader("userId");
        if (userId == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //更新阅读次数
        String readBehaviorJson = (String) cacheService.hGet(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(), userId);
        if (StringUtils.isNotBlank(readBehaviorJson)) {
            ReadBehaviorDto readBehaviorDto = JSON.parseObject(readBehaviorJson, ReadBehaviorDto.class);
            dto.setCount( readBehaviorDto.getCount() + dto.getCount());
        }
        // 保存当前key
        log.info("保存当前key:{} {} {}", dto.getArticleId(), userId, dto);
        cacheService.hPut(BehaviorConstants.READ_BEHAVIOR + dto.getArticleId().toString(), userId, JSON.toJSONString(new Date()));

        //发送消息，数据聚合
        UpdateArticleMess mess = new UpdateArticleMess();
        mess.setArticleId(dto.getArticleId());
        mess.setType(UpdateArticleMess.UpdateArticleType.VIEWS);
        mess.setAdd(1);

        kafkaTemplate.send(HotArticleConstants.HOT_ARTICLE_SCORE_TOPIC,JSON.toJSONString(mess));
        
        
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }
}