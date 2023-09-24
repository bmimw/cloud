package com.jshy.commodity.controller.v1;

import com.jshy.commodity.service.CmRecommendService;
import com.jshy.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hot")
@Api(value = "小程序推荐模块", tags = "小程序推荐模块")
public class HotController {

    @Autowired
    CmRecommendService cmRecommendService;
    @GetMapping("/preference")
    @ApiOperation("特惠推荐")
    public ResponseResult getHotPreference(){
        return cmRecommendService.findIndexRecommendDetails("1");
    }
    @GetMapping("/inVogue")
    @ApiOperation("爆款推荐")
    public ResponseResult getHotInVogue(){
        return cmRecommendService.findIndexRecommendDetails("2");
    }
    @GetMapping("/oneStop")
    @ApiOperation("一站买全")
    public ResponseResult getHotOneStop(){
        return cmRecommendService.findIndexRecommendDetails("3");
    }
    @GetMapping("/new")
    @ApiOperation("新鲜好物")
    public ResponseResult getHotNew(){
        return cmRecommendService.findIndexRecommendDetails("4");
    }
}
