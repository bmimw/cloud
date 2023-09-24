package com.jshy.wemedia.controller.v1;

import com.baomidou.mybatisplus.extension.api.R;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.dtos.WmCreatedDto;
import com.jshy.model.wemedia.dtos.WmLoginDto;
import com.jshy.wemedia.service.WmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "商家用户登录", tags = "电脑端的用户登录")
public class LoginController {

    @Autowired
    private WmUserService wmUserService;

    @PostMapping("/in")
    @ApiOperation("商家用户登录")
    public ResponseResult login(@RequestBody WmLoginDto dto) {
        return wmUserService.login(dto);
    }

}
