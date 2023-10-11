package com.jshy.user.controller.v1;

import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.user.dtos.UserRelationDto;
import com.jshy.user.service.ApUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "app端口的用户关注",tags = "app端口的用户关注")
public class ApUserFollowController {

    @Autowired
    ApUserService apUserService;

    @PostMapping("/user_follow")
    @ApiOperation("app端口的用户关注")
    public ResponseResult userFollow(@RequestBody UserRelationDto userRelationDto,HttpServletRequest request){
        return apUserService.userFollow(request,userRelationDto);
    }
}
