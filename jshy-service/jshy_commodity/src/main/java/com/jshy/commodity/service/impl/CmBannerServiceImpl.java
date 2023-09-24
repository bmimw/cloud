package com.jshy.commodity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jshy.commodity.mapper.CmBannerMapper;
import com.jshy.commodity.service.CmBannerService;
import com.jshy.model.commodity.pojos.CmBanner;
import com.jshy.model.common.dtos.ResponseResult;
import com.jshy.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CmBannerServiceImpl extends ServiceImpl<CmBannerMapper, CmBanner> implements CmBannerService {

    @Override
    public ResponseResult findBannerType(Integer type) {
        //检查下参数是否合规
        if (type==null && type==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        List<CmBanner> cmBanner = list(Wrappers.<CmBanner>lambdaQuery().eq(CmBanner::getType, type));
        return ResponseResult.okResult(cmBanner);
    }
}
