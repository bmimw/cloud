package com.jshy.apis.article.fallback;

import com.jshy.apis.article.IArticleClient;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.article.dtos.CollectionBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * feign失败配置
 * @author itjshy
 */
@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public ResponseResult saveArticle(ArticleDto dto)  {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }


    @Override
    public ResponseResult collectionBehavior(CollectionBehaviorDto collectionBehaviorDto, HttpServletRequest request) {
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
    }
//
//    @Override
//    public Boolean findApArticleById(Long id) {
//        return false;
//    }
}