package com.jshy.article.feign;

import com.jshy.apis.article.IArticleClient;
import com.jshy.article.service.ApArticleService;
import com.jshy.model.article.dtos.ArticleDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleService apArticleService;

    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }

}