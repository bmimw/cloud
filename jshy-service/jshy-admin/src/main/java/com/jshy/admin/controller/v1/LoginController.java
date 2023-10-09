package com.jshy.admin.controller.v1;

import com.jshy.admin.service.AdUserService;
import com.jshy.model.admin.dtos.AdLoginDto;
import com.jshy.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "管理员登录", tags = "管理员登录")
public class LoginController {

    @Autowired
    private AdUserService adUserService;

    @PostMapping("/in")
    @ApiOperation("管理员登录")
    public ResponseResult login(@RequestBody AdLoginDto dto) {
        return adUserService.login(dto);
    }

}
