package com.jshy.wemedia.controller.v1;

import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
@Api(value = "文章频道",tags = "文章频道")
public class WmchannelController {

    @Autowired
    WmChannelService wmChannelService;

    @GetMapping("/channels")
    @ApiOperation("文章分类遍历")
    public ResponseResult findAll(){
        return wmChannelService.findAll();
    }
}