package com.jshy.article.controller.v1;

import com.jshy.article.service.ApArticleService;
import com.jshy.common.constants.ArticleConstants;
import com.jshy.model.article.dtos.ArticleHomeDto;
import com.jshy.model.article.dtos.ArticleInfoDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 加载首页
     * @param dto
     * @return
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto){
        //        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
        return apArticleService.load2(dto, ArticleConstants.LOADTYPE_LOAD_MORE,true);
    }

    @PostMapping("/loadmore")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(ArticleConstants.LOADTYPE_LOAD_MORE,dto);
    }

    @PostMapping("/loadnew")
    public ResponseResult loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(ArticleConstants.LOADTYPE_LOAD_NEW,dto);
    }

    @PostMapping("/load_article_behavior")
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto articleInfoDto, HttpServletRequest request){
        return apArticleService.loadArticleBehavior(articleInfoDto,request);

    }
}