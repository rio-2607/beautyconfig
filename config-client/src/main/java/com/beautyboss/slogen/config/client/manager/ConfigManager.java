package com.beautyboss.slogen.config.client.manager;

import java.util.List;
import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public interface ConfigManager {

    String get(String key);

    String get(String key,String defaultValue);

    Byte getByteValue(String key);

    byte getByteValue(String key, byte defaultValue);

    Short getShortValue(String key);

    short getShortValue(String key,short defaultValue);

    Integer getIntValue(String key);

    int getIntValue(String key,int defaultValue);

    Long getLongValue(String key);

    long getLongValue(String key,long defaultValue);

    Float getFloatValue(String key);

    float getFloatValue(String key,float defaultValue);

    Double getDoubleValue(String key);

    double getDoubleValue(String key,double defaultValue);

    Boolean getBooleanValue(String key);

    boolean getBooleanValue(String key,boolean defaultValue);

    List<String> getList(String key);

    <T> List<T> getList(String key, Class<T> clazz);

    <T> List<T> getList(String key, Class<T> clazz, List<T> defaultValue);

    Map<String, String> getMap(String key);

    <T> Map<String, T> getMap(String key, Class<T> clazz);

    <T> Map<String, T> getMap(String key, Class<T> clazz, Map<String, T> defaultValue);

    <T> T getBean(String key, Class<T> clazz);

    <T> T getBean(String key, Class<T> clazz, T defaultValue);

    String get(String key, String defaultValue, ConfigContext context);

}
