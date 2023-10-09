package com.jshy.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdSensitiveDto;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.wemedia.pojos.WmSensitive;
import com.jshy.wemedia.mapper.WmSensitiveMapper;
import com.jshy.wemedia.service.WmSensitiveService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {
    /**
     * 分页、关键词查询敏感词列表
     * */
    @Override
    public ResponseResult list(AdChannelDto adChannelDto) {
        //先对参数进行检查
        adChannelDto.checkParam();

        IPage page = new Page(adChannelDto.getPage(), adChannelDto.getSize());
        LambdaQueryWrapper<WmSensitive> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //条件封装
        lambdaQueryWrapper.like(StringUtils.isNotBlank(adChannelDto.getName()), WmSensitive::getSensitives, adChannelDto.getName());
        lambdaQueryWrapper.orderByDesc(WmSensitive::getCreatedTime);
        //进行查询
        page = page(page, lambdaQueryWrapper);

        //结果返回
        ResponseResult responseResult = new PageResponseResult(adChannelDto.getPage(), adChannelDto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }
    /**
     * 删除敏感词
     * */
    @Override
    public ResponseResult del(Integer id) {
        //先校验参数
        if (id==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        WmSensitive wmSensitive=getById(id);
        //sql数据校验
        if (wmSensitive==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //删除数据
        removeById(wmSensitive.getId());

        return ResponseResult.okResult(wmSensitive);
    }
    /**
     * 新增敏感词
     * */
    @Override
    public ResponseResult save(AdSensitiveDto adSensitiveDto) {
        //校验参数
        if (StringUtils.isBlank(adSensitiveDto.getSensitives())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"敏感词不可为空");
        }
        //查询敏感词是否重复了
        WmSensitive wmSensitive=getOne(Wrappers.<WmSensitive>lambdaQuery().eq(WmSensitive::getSensitives,adSensitiveDto.getSensitives()));
        if (wmSensitive!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"当前敏感词已经存在了");
        }
        //初始化
        wmSensitive=new WmSensitive();
        //属性的拷贝
        wmSensitive.setSensitives(adSensitiveDto.getSensitives());
        wmSensitive.setCreatedTime(new Date());
        //新建数据
        save(wmSensitive);
        return ResponseResult.okResult(wmSensitive);
    }
    /**
     * 修改敏感词
     * */
    @Override
    public ResponseResult update(AdSensitiveDto adSensitiveDto) {
        //参数检验
        if (adSensitiveDto.getId()==null || StringUtils.isBlank(adSensitiveDto.getSensitives())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"频道名称不可为空");
        }
        //查询敏感词是否重复了
        WmSensitive wmSensitive=getOne(Wrappers.<WmSensitive>lambdaQuery().eq(WmSensitive::getSensitives,adSensitiveDto.getSensitives()));
        if (wmSensitive!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"当前敏感词已经存在了");
        }
        //初始化
        wmSensitive=new WmSensitive();
        //数据拷贝
        BeanUtils.copyProperties(adSensitiveDto,wmSensitive);
        //根据id进行修改
        updateById(wmSensitive);
        return ResponseResult.okResult(wmSensitive);
    }
}
