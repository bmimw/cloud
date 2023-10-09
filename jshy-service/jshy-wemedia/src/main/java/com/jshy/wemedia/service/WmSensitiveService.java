package com.jshy.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdSensitiveDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    /**
     * 分页、关键词查询敏感词列表
     */
    public ResponseResult list(AdChannelDto adChannelDto);

    /**
     * 删除敏感词
     */
    public ResponseResult del(Integer id);

    /**
     * 新增敏感词
     */
    public ResponseResult save(AdSensitiveDto adSensitiveDto);

    /**
     * 修改敏感词
     */
    public ResponseResult update(AdSensitiveDto adSensitiveDto);
}

