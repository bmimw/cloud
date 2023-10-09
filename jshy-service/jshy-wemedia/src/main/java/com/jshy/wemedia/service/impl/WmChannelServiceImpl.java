package com.jshy.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdChannelUpDateDto;
import com.jshy.model.common.dtos.PageResponseResult;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import com.jshy.model.wemedia.pojos.WmChannel;
import com.jshy.model.wemedia.pojos.WmNews;
import com.jshy.wemedia.mapper.WmChannelMapper;
import com.jshy.wemedia.mapper.WmNewsMapper;
import com.jshy.wemedia.service.WmChannelService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {


    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    @Override
    public ResponseResult findChannel(AdChannelDto adChannelDto) {
        //先对分页参数的校验。
        adChannelDto.checkParam();

        IPage page = new Page(adChannelDto.getPage(), adChannelDto.getSize());
        LambdaQueryWrapper<WmChannel> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //条件封装
        lambdaQueryWrapper.like(StringUtils.isNotBlank(adChannelDto.getName()), WmChannel::getName, adChannelDto.getName());
        lambdaQueryWrapper.orderByDesc(WmChannel::getCreatedTime);

        //进行查询
        page = page(page, lambdaQueryWrapper);

        //3.结果返回
        ResponseResult responseResult = new PageResponseResult(adChannelDto.getPage(), adChannelDto.getSize(), (int) page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }

    /**
     * 修改频道信息
     */
    @Autowired
    WmNewsMapper wmNewsMapper;
    @Override
    public ResponseResult updateChannel(AdChannelUpDateDto adChannelUpDateDto) {
        //先对参数进行校验
        if (adChannelUpDateDto.getStatus().equals(null) || adChannelUpDateDto.getId().equals(null) || StringUtils.isBlank(adChannelUpDateDto.getName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道不存在以及名称不可为空");
        }
        //查询出对应的信息
        WmChannel wmChannel = getById(adChannelUpDateDto.getId());
        //sql校验
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        //查询是否正在被引用
        List<WmNews> wmNews=wmNewsMapper.selectList(Wrappers.<WmNews>lambdaQuery().eq(WmNews::getChannelId,adChannelUpDateDto.getId()));
        if (!wmNews.isEmpty() && !adChannelUpDateDto.getStatus()){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道已经被引用不可禁用");
        }

        wmChannel.setName(adChannelUpDateDto.getName());
        wmChannel.setStatus(adChannelUpDateDto.getStatus());

        if (adChannelUpDateDto.getDescription() != null)
            wmChannel.setDescription(adChannelUpDateDto.getDescription());
        if (adChannelUpDateDto.getOrd() != null)
            wmChannel.setOrd(adChannelUpDateDto.getOrd());

        updateById(wmChannel);

        return ResponseResult.okResult(wmChannel);
    }

    /**
     * 根据id删除对应的频道
     */
    @Override
    public ResponseResult delChannelById(Integer id) {
        //先校验参数
        if (id <= 0 || id.equals(null)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "id错误");
        }
        WmChannel wmChannel = getById(id);
        //sql校验
        if (wmChannel == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        // 检验是否被禁用了
        if (wmChannel.getStatus()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "该频道还正在被启动，请先禁用");
        }
        removeById(wmChannel.getId());

        return  ResponseResult.okResult(wmChannel);
    }

    /**
     * 新增加频道
     */
    @Override
    public ResponseResult saveChannel(AdChannelUpDateDto adChannelUpDateDto) {
        //先对参数进行校验
        if (StringUtils.isBlank(adChannelUpDateDto.getName()) || StringUtils.isBlank(adChannelUpDateDto.getDescription()) || adChannelUpDateDto.getOrd()==null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道名称和排序方式以及描述不可为空");
        }
        if (adChannelUpDateDto.getOrd()>255){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "频道排序方式不可大于255");
        }
        //检查是否已经存在
        WmChannel wmChannel = getOne(Wrappers.<WmChannel>lambdaQuery().eq(WmChannel::getName,adChannelUpDateDto.getName()));
        if (wmChannel!=null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST, "该频道名称已经创建过了");
        }
        //对数据进行拷贝
        BeanUtils.copyProperties(adChannelUpDateDto, wmChannel);
        wmChannel.setCreatedTime(new Date());
        wmChannel.setIsDefault(false);
        //进行新建频道
        save(wmChannel);

        return ResponseResult.okResult(wmChannel);
    }
}