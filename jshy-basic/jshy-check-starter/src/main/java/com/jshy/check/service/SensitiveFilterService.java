package com.jshy.check.service;

public interface SensitiveFilterService {
    /**
     * 判断是否有关键字
     *
     */
    public Boolean judgeSensitive(String text);

    /**
     * 过滤内容
     *
     */
    public String filterSensitive(String source);
}
