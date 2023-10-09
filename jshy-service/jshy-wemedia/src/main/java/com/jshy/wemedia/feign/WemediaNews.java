package com.jshy.wemedia.feign;

import com.jshy.apis.wemedia.IWemediaNews;
import com.jshy.model.admin.dtos.AdWemediaNewsDto;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WemediaNews implements IWemediaNews {

    @Autowired
    WmNewsService wmNewsService;

    /**
     * 分页条件查询文章
     */
    @Override
    public ResponseResult findListVo(AdWemediaNewsDto adWemediaNewsDto) {
        return wmNewsService.findListVo(adWemediaNewsDto);
    }

    /**
     * 查询文章详情
     */
    @Override
    public ResponseResult findOneVo(Integer id) {
        return wmNewsService.findOneVo(id);
    }

    /**
     * 审核失败
     */
    @Override
    public ResponseResult authFail(AdWemediaNewsDto adWemediaNewsDto) {
        return wmNewsService.authFail(adWemediaNewsDto);
    }

    /**
     * 审核成功
     */
    @Override
    public ResponseResult authPass(AdWemediaNewsDto adWemediaNewsDto) {
        return wmNewsService.authPass(adWemediaNewsDto);
    }
}
