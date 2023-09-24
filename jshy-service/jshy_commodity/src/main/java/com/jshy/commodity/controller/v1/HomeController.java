package com.jshy.commodity.controller.v1;

import com.jshy.commodity.service.CmBannerService;
import com.jshy.commodity.service.CmGoodsService;
import com.jshy.commodity.service.CmParentService;
import com.jshy.commodity.service.CmRecommendService;
import com.jshy.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@Api(value = "小程序首页模块", tags = "小程序首页模块")
public class HomeController {

    @Autowired
    CmBannerService bannerService;

    @GetMapping("/banner")
    @ApiOperation("轮播图接口")
    public ResponseResult getHomeBanner(@RequestParam(name = "distributionSite") Integer distributionSite) {
        return bannerService.findBannerType(distributionSite);
    }

    @Autowired
    CmParentService cmParentService;

    @GetMapping("/category/mutli")
    @ApiOperation("首页分类栏框")
    public ResponseResult getHomeCategory(){
        return cmParentService.findIndexParent();
    }

    @Autowired
    CmRecommendService cmRecommendService;

    @GetMapping("/hot/mutli")
    @ApiOperation("首页的推荐栏")
    public ResponseResult getHomeRecommend(){
        return cmRecommendService.findIndexRecommend();
    }

    @Autowired
    CmGoodsService cmGoodsService;

    @GetMapping("/goods/guessLike")
    @ApiOperation("首页猜你喜欢模块")
    public ResponseResult getHomeGuessLike(@RequestParam(name = "page", required = false) Integer page,
                                           @RequestParam(name = "pageSize", required = false) Integer pageSize){
        return cmGoodsService.getGuessLike(page,pageSize);
    }
}
