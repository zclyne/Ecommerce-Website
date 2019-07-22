package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

// 用于存放token并设置其有效期的类
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    private static LoadingCache<String, String> localCache =
            CacheBuilder.newBuilder()
                .initialCapacity(1000) // 初始大小
                .maximumSize(10000) // 当达到此大小时，将使用LRU算法进行淘汰
                .expireAfterAccess(12, TimeUnit.HOURS) // 缓存有效期为12小时
                .build(new CacheLoader<String, String>() {
                    // 默认的数据加载实现。当调用get取值的时候，若key没有对应的value，就调用该方法进行加载
                    @Override
                    public String load(String s) throws Exception {
                        return "null";
                    }
                });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache get error", e);
        }
        return null;
    }

}
