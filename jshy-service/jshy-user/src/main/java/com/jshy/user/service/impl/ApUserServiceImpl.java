package com.jshy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.user.dtos.LoginDto;
import com.jshy.model.user.dtos.RegisterDto;
import com.jshy.model.user.pojos.ApUser;
import com.jshy.user.mapper.ApUserMapper;
import com.jshy.user.service.ApUserService;
import com.jshy.utils.common.AppJwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    /**
     * app端的用户登录
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        //1.先处理正常登录的（手机号+密码登录）并且两个值都不为空
        if (!StringUtils.isBlank(dto.getPhone()) && !StringUtils.isBlank(dto.getPassword())) {
            //1.1使用MyBatis_plus查询手机号所对应的单个用户
            ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (apUser == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户不存在");
            }
            //1.2 比对密码
            String salt = apUser.getSalt();
            String pswd = dto.getPassword();
            pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
            if (!pswd.equals(apUser.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            //1.3 返回数据  jwt
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        } else {
            //2.游客  同样返回token  id = 0
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(0l));
            return ResponseResult.okResult(map);
        }
    }

    /**
     * 用户注册
     */
    @Override
    public ResponseResult register(RegisterDto dto) {
        //1.检查参数
        if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword()) || StringUtils.isBlank(dto.getPhone())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "名称、密码、手机号不可为空");
        }
        //查看手机号是否被注册过
        ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
        if (apUser == null) {
            //先进行初始化，要不然会会报null
            apUser = new ApUser();
            //手机号没有被注册过，将数据拷贝95152
            BeanUtils.copyProperties(dto, apUser);
            //对密码进行加密
            //生成盐  [10000,20000)
            apUser.setSalt(String.valueOf(Math.random() * 10000 + 10000));
            //将密码进行md5加盐加密
            apUser.setPassword(DigestUtils.md5DigestAsHex((apUser.getPassword() + apUser.getSalt()).getBytes()));
            //添加注册时间
            apUser.setCreatedTime(new Date());
            //操作数据库添加用户
            save(apUser);
            //返回数据直接登录  jwt
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(apUser.getId().longValue()));
            apUser.setSalt("");
            apUser.setPassword("");
            map.put("user", apUser);
            return ResponseResult.okResult(map);
        } else {
            //手机号被注册了返回错误
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "手机号已被注册过");
        }
    }

}