package com.jshy.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.article.dtos.ArticleHomeDto;
import com.jshy.model.article.pojos.ApArticle;
import com.jshy.model.common.dtos.ResponseResult;

import java.io.IOException;

public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载文章列表
     * @param loadtype 1为加载更多  2为加载最新
     * @param dto
     * @return
     */
    ResponseResult load(Short loadtype, ArticleHomeDto dto);

    /**
     * 保存app端相关文章
     * @param dto
     * @return
     */
    ResponseResult saveArticle(ArticleDto dto) ;
}