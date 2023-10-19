package com.jshy.behavior.service;

import com.jshy.model.behavior.dtos.ReadBehaviorDto;
import com.jshy.model.common.dtos.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface ApReadBehaviorService {
    /**
     * 用户阅读
     */
    ResponseResult readBehavior(ReadBehaviorDto readBehaviorDto, HttpServletRequest request);
}
