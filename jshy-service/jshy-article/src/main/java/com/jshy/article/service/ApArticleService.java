package com.jshy.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.article.dtos.ArticleHomeDto;
import com.jshy.model.article.dtos.ArticleInfoDto;
import com.jshy.model.article.dtos.CollectionBehaviorDto;
import com.jshy.model.article.mess.ArticleVisitStreamMess;
import com.jshy.model.article.pojos.ApArticle;
import com.jshy.model.common.dtos.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载文章列表
     *
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    ResponseResult load(Short loadtype, ArticleHomeDto dto);

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return
     */
    ResponseResult saveArticle(ArticleDto dto);

    /**
     * 用户收藏
     */
    ResponseResult collectionBehavior(HttpServletRequest request, CollectionBehaviorDto collectionBehaviorDto);

    /**
     *用户回显
     * */
    ResponseResult loadArticleBehavior(ArticleInfoDto articleInfoDto, HttpServletRequest request);
    /**
     * 加载文章列表
     * @param dto
     * @param type  1 加载更多   2 加载最新
     * @param firstPage  true  是首页  flase 非首页
     * @return
     */
    public ResponseResult load2(ArticleHomeDto dto,Short type,boolean firstPage);

    /**
     * 更新文章的分值  同时更新缓存中的热点文章数据
     * @param mess
     */
    public void updateScore(ArticleVisitStreamMess mess);
}