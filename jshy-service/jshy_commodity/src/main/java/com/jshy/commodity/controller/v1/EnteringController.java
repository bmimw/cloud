package com.jshy.commodity.controller.v1;

import com.jshy.model.commodity.dtos.CmEnteringDto;
import com.jshy.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entering")
@Api(value = "商品的录入", tags = "商品的录入")
public class EnteringController {

    @PostMapping("/in")
    @ApiOperation("商品的录入")
    public ResponseResult login(@RequestBody CmEnteringDto dto) {
        return null;
    }

}
