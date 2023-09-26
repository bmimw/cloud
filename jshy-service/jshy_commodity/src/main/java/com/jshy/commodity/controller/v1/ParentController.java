package com.jshy.commodity.controller.v1;

import com.jshy.commodity.service.CmParentService;
import com.jshy.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(value = "一二级分类模块", tags = "一二级分类模块")
public class ParentController {


    @Autowired
    CmParentService cmParentService;

    @GetMapping("/top")
    @ApiOperation("分类一二级展示")
    public ResponseResult Top(){
        return cmParentService.findCategoryParent();
    }
}
