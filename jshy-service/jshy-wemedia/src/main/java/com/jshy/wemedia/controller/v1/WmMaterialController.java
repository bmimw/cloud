package com.jshy.wemedia.controller.v1;

import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmMaterialDto;
import com.jshy.wemedia.service.WmMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
@Api(value = "商家素材接口", tags = "商家素材接口")
public class WmMaterialController {

    @Autowired
    WmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    @ApiOperation("用户素材上传接口")
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @PostMapping("/list")
    @ApiOperation("用户素材遍历接口")
    public ResponseResult findList(@RequestBody WmMaterialDto dto) {
        return wmMaterialService.findList(dto);
    }

    @GetMapping("/del_picture/{id}")
    @ApiOperation("删除素材接口")
    public ResponseResult del_picture(@PathVariable Integer id) {
        return wmMaterialService.del_picture(id);
    }

    @GetMapping("/collect/{id}")
    @ApiOperation("素材收藏接口")
    public ResponseResult collect(@PathVariable Integer id) {
        return wmMaterialService.collect(id);
    }
    @GetMapping("/cancel_collect/{id}")
    @ApiOperation("素材取消收藏")
    public ResponseResult cancel_collect(@PathVariable Integer id){
        return wmMaterialService.cancel_collect(id);
    }
}
