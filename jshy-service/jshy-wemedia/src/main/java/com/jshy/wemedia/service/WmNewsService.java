package com.jshy.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.admin.dtos.AdWemediaNewsDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmNewsDto;
import com.jshy.model.wemedia.dtos.WmNewsPageReqDto;
import com.jshy.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章
     *
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);

    /**
     * 发布文章或保存草稿
     *
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 文章的上下架
     *
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 文章的人工审批
     */
    public ResponseResult findListVo(AdWemediaNewsDto adWemediaNewsDto);

    /**
     * 查询文章详情
     */
    public ResponseResult findOneVo(Integer id);

    /**
     * 审核失败
     */
    public ResponseResult authFail(AdWemediaNewsDto adWemediaNewsDto);

    /**
     * 审核成功
     */
    public ResponseResult authPass(AdWemediaNewsDto adWemediaNewsDto);
}