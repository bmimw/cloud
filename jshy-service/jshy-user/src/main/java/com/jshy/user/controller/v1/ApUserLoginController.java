package com.jshy.user.controller.v1;

import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.user.dtos.LoginDto;
import com.jshy.model.user.dtos.RegisterDto;
import com.jshy.user.service.ApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@Api(value = "app端口的用户登录",tags = "app端口的用户登录")
public class ApUserLoginController {

    @Autowired
    private ApUserService apUserService;

    @PostMapping("/login_auth")
    @ApiOperation("用户登录验证接口")
    public ResponseResult login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public ResponseResult register(@RequestBody RegisterDto dto) {
        return apUserService.register(dto);
    }
//    @PostMapping("/reset")
//    @ApiOperation("忘记密码、重置密码接口")
//    public ResponseResult reset(@RequestBody ResetDto dto){
//        return  apUserService.resetpassword(dto);
//    }
}