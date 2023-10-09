package com.jshy.apis.wemedia;

import com.jshy.apis.wemedia.fallback.IWemediaNewsFallback;
import com.jshy.model.admin.dtos.AdWemediaNewsDto;
import com.jshy.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "jshy-wemediaNews", fallback = IWemediaNewsFallback.class)
public interface IWemediaNews {
    /**
     * 分页条件查询文章
     */
    @PostMapping("/api/v1/news/list_vo")
    public ResponseResult findListVo(@RequestBody AdWemediaNewsDto adWemediaNewsDto);

    /**
     * 查询文章详情
     */
    @GetMapping("/api/v1/news/one_vo/{id}")
    public ResponseResult findOneVo(@PathVariable Integer id);

    /**
     * 审核失败
     */
    @PostMapping("/api/v1/news/auth_fail")
    public ResponseResult authFail(@RequestBody AdWemediaNewsDto adWemediaNewsDto);

    /**
     * 审核成功
     */
    @PostMapping("/api/v1/news/auth_pass")
    public ResponseResult authPass(@RequestBody AdWemediaNewsDto adWemediaNewsDto);
}
