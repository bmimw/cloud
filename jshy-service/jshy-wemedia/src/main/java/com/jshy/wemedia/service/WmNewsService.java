package com.jshy.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmNewsDto;
import com.jshy.model.wemedia.dtos.WmNewsPageReqDto;
import com.jshy.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     *  发布文章或保存草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

}