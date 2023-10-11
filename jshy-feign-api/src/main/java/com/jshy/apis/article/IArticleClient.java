package com.jshy.apis.article;

import com.jshy.apis.article.fallback.IArticleClientFallback;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.article.dtos.CollectionBehaviorDto;
import com.jshy.model.article.pojos.ApArticle;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "jshy-article",fallback = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);

    @PostMapping("/api/v1/collection_behavior")
    public ResponseResult collectionBehavior(@RequestBody CollectionBehaviorDto collectionBehaviorDto,HttpServletRequest request);
//    public Boolean findApArticleById(Long id);
}