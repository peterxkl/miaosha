package com.miaoshaproject.service;

//操作本地缓存的操作类
public interface CacheService {
    //存方法
    void setCommonCache(String key, Object value);

    //取方法
    Object getCommonCache(String key);
}
