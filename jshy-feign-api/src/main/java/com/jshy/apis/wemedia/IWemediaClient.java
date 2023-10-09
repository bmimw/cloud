package com.jshy.apis.wemedia;

import com.jshy.apis.wemedia.fallback.IWemediaClientFallback;
import com.jshy.model.admin.dtos.AdChannelDto;
import com.jshy.model.admin.dtos.AdChannelUpDateDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jshy-wemedia", fallback = IWemediaClientFallback.class)
public interface IWemediaClient {
    /**
     * 分页条件查询频道
     */
    @PostMapping("/api/v1/channel/list")
    public ResponseResult findChannel(@RequestBody AdChannelDto adChannelDto);

    /**
     * 更改频道信息
     */
    @PostMapping("api/v1/channel/update")
    public ResponseResult updateChannel(@RequestBody AdChannelUpDateDto adChannelUpDateDto);

    /**
     * 删除对应频道
     */
    @GetMapping("api/v1/channel/del/{id}")
    public ResponseResult delChannelById(@PathVariable Integer id);

    /**
     * 新增频道
     */
    @PostMapping("api/v1/channel/save")
    public ResponseResult saveChannel(@RequestBody AdChannelUpDateDto adChannelUpDateDto);

}
