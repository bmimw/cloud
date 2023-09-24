package com.jshy.search.service;

import com.jshy.model.search.dtos.UserSearchDto;
import com.jshy.model.common.dtos.ResponseResult;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     ES文章分页搜索
     @return
     */
    ResponseResult search(UserSearchDto userSearchDto) throws IOException;
}