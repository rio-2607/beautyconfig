package com.beautyboss.slogen.config.common.utils;

/**
 * Author : Slogen
 * Date   : 2019/2/15
 */
public class LocalCacheFactory {

    private static final int DEFAULT_MAX_SIZE = 1000;
    private static final int DEFAULT_EXPIRE_TIME = 60000;


    public static <V> LocalCache<String,V> createStringKeyCache(Integer maxSize,Integer expireTime) {
        return createCache(maxSize, expireTime);
    }

    public static <K,V> LocalCache<K,V> createCache(Integer maxSize,Integer expireTime) {
        LocalCache<K,V> cache = new LocalCache<>((Integer) defautlValueIfNull(maxSize,DEFAULT_MAX_SIZE),(Integer)defautlValueIfNull(expireTime,DEFAULT_EXPIRE_TIME));
        return cache;
    }

    private static Object defautlValueIfNull(Object object,Object defaultValue) {
        return null == object ? defaultValue : object;
    }

}
