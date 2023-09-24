package com.jshy.wemedia.controller.v1;

import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmNewsDto;
import com.jshy.model.wemedia.dtos.WmNewsPageReqDto;
import com.jshy.wemedia.service.WmNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/news")
@Api(value = "文章查询",tags = "文章查询")
public class WmNewsController {

    @Autowired
    WmNewsService wmNewsService;

    @PostMapping("/list")
    @ApiOperation("文章遍历查询")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    @PostMapping("/submit")
    @ApiOperation("添加文章")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return  wmNewsService.submitNews(dto);
    }
}