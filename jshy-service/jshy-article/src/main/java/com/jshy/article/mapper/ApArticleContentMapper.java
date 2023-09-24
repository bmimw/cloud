package com.jshy.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jshy.model.article.pojos.ApArticle;
import com.jshy.model.article.pojos.ApArticleContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApArticleContentMapper extends BaseMapper<ApArticleContent> {
}
