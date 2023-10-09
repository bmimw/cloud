package com.jshy.apis.wemedia;

import com.jshy.apis.wemedia.fallback.IWemediaSensitiveFallback;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdSensitiveDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jshy-wemediaSensitive", fallback = IWemediaSensitiveFallback.class)
public interface IWemediaSensitive {
    /**
     * 分页、关键词查询敏感词列表
     */
    @PostMapping("api/v1/sensitive/list")
    public ResponseResult list(@RequestBody AdChannelDto adChannelDto);

    /**
     * 删除敏感词根据id
     */
    @DeleteMapping("api/v1/sensitive/del/{id}")
    public ResponseResult del(@PathVariable Integer id);

    /**
     * 新增敏感词
     */
    @PostMapping("api/v1/sensitive/save")
    public ResponseResult save(@RequestBody AdSensitiveDto adSensitiveDto);

    /**
     * 修改敏感词
     */
    @PostMapping("api/v1/sensitive/update")
    public ResponseResult update(@RequestBody AdSensitiveDto adSensitiveDto);
}
