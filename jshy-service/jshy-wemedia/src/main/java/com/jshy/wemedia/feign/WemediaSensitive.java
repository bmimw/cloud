package com.jshy.wemedia.feign;

import com.jshy.apis.wemedia.IWemediaSensitive;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdSensitiveDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.wemedia.service.WmSensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WemediaSensitive implements IWemediaSensitive {

    @Autowired
    WmSensitiveService wmSensitiveService;

    /**
     * 分页、关键词查询敏感词列表
     */
    @Override
    public ResponseResult list(AdChannelDto adChannelDto) {
        return wmSensitiveService.list(adChannelDto);
    }

    /**
     * 删除敏感词
     */
    @Override
    public ResponseResult del(Integer id) {
        return wmSensitiveService.del(id);
    }

    /**
     * 新增敏感词
     */
    @Override
    public ResponseResult save(AdSensitiveDto adSensitiveDto) {
        return wmSensitiveService.save(adSensitiveDto);
    }

    /**
     * 修改敏感词
     */
    @Override
    public ResponseResult update(AdSensitiveDto adSensitiveDto) {
        return wmSensitiveService.update(adSensitiveDto);
    }
}
