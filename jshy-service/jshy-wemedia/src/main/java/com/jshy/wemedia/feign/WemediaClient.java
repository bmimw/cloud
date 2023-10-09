package com.jshy.wemedia.feign;

import com.jshy.apis.wemedia.IWemediaClient;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdChannelUpDateDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.wemedia.service.WmChannelService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WemediaClient implements IWemediaClient {

    @Autowired
    private WmChannelService wmChannelService;

    /**
     * 分页，模糊查询频道
     */
    @Override
    public ResponseResult findChannel(AdChannelDto adChannelDto) {
        return wmChannelService.findChannel(adChannelDto);
    }

    /**
     * 更改频道信息
     */
    @Override
    public ResponseResult updateChannel(AdChannelUpDateDto adChannelUpDateDto) {
        return wmChannelService.updateChannel(adChannelUpDateDto);
    }

    /**
     * 根据id删除对应的频道
     */
    @Override
    public ResponseResult delChannelById(Integer id) {
        return wmChannelService.delChannelById(id);
    }

    /**
     * 新建频道
     */
    @Override
    public ResponseResult saveChannel(AdChannelUpDateDto adChannelUpDateDto) {
        return wmChannelService.saveChannel(adChannelUpDateDto);
    }
}