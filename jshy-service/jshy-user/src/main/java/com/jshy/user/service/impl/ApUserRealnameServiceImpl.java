package com.jshy.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.model.admin.dtos.AdUserDto;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.user.pojos.ApUser;
import com.jshy.model.user.pojos.ApUserRealname;
import com.jshy.user.mapper.ApUserRealnameMapper;
import com.jshy.user.service.ApUserRealnameService;
import org.springframework.stereotype.Service;

@Service
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    /**
     * 分页条件查询用户
     */
    @Override
    public ResponseResult findUserList(AdUserDto adUserDto) {
        //分页参数的校验
        adUserDto.checkParam();
        IPage page = new Page(adUserDto.getPage(), adUserDto.getSize());
        LambdaQueryWrapper<ApUserRealname> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //其他参数的校验
        if (adUserDto.getStatus() != null) {
            lambdaQueryWrapper.eq(ApUserRealname::getStatus, adUserDto.getStatus());
        }
        page = page(page, lambdaQueryWrapper);
        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(adUserDto.getPage(), adUserDto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }

    /**
     * 审核成功
     */
    @Override
    public ResponseResult authPass(AdUserDto adUserDto) {
        //检查参数
        if (adUserDto.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApUserRealname apUserRealname=getById(adUserDto.getId());
        //校验sql
        if (apUserRealname==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        apUserRealname.setStatus((short) 9);
        updateById(apUserRealname);
        return ResponseResult.okResult(apUserRealname);
    }

    /**
     * 审核失败
     */
    @Override
    public ResponseResult authFail(AdUserDto adUserDto) {
        //检查参数
        if (adUserDto.getId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApUserRealname apUserRealname=getById(adUserDto.getId());
        //校验sql
        if (apUserRealname==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        apUserRealname.setStatus((short) 2);
        apUserRealname.setReason(adUserDto.getMsg());
        updateById(apUserRealname);
        return ResponseResult.okResult(apUserRealname);
    }
}
