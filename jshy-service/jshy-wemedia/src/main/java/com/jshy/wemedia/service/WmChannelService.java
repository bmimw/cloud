package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdChannelUpDateDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.pojos.WmChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     *
     * @return
     */
    public ResponseResult findAll();

    /**
     * 分页，模糊查询频道
     */
    public ResponseResult findChannel(AdChannelDto adChannelDto);

    /**
     * 修改频道信息
     */
    public ResponseResult updateChannel(AdChannelUpDateDto adChannelUpDateDto);

    /**
     * 根据id删除对应的频道
     */
    public ResponseResult delChannelById(Integer id);

    /**
     * 新增加频道
     */
    public ResponseResult saveChannel(AdChannelUpDateDto adChannelUpDateDto);
}