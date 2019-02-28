package com.beautyboss.slogen.config.client.manager;

import com.beautyboss.slogen.config.client.util.StringUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public abstract class AbstractConfigManager implements ConfigManager {

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected abstract String doGet(String key, String defaultValue, ConfigContext context);

    @Override
    public String get(String key) {
        key = StringUtils.trimToNull(key);
        if(StringUtils.isEmpty(key)) {
            throw new NullPointerException("key is null");
        }
        return get(key,null);
    }

    @Override
    public String get(String key, String defaultValue) {
        key = StringUtils.trimToNull(key);
        if(StringUtils.isEmpty(key)) {
            throw new NullPointerException("key is null");
        }
        return doGet(key,defaultValue,null);
    }

    @Override
    public Byte getByteValue(String key) {
        String value = get(key);
        return value == null ? null : Byte.valueOf(value);
    }

    @Override
    public byte getByteValue(String key, byte defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Byte.parseByte(value);
    }

    @Override
    public Short getShortValue(String key) {
        String value = get(key);
        return value == null ? null : Short.valueOf(value);
    }

    @Override
    public short getShortValue(String key, short defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Short.parseShort(value);
    }

    @Override
    public Integer getIntValue(String key) {
        String value = get(key);
        return value == null ? null : Integer.valueOf(value);
    }

    @Override
    public int getIntValue(String key, int defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    @Override
    public Long getLongValue(String key) {
        String value = get(key);
        return value == null ? null : Long.valueOf(value);
    }

    @Override
    public long getLongValue(String key, long defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Long.parseLong(value);
    }

    @Override
    public Float getFloatValue(String key) {
        String value = get(key);
        return value == null ? null : Float.valueOf(value);
    }

    @Override
    public float getFloatValue(String key, float defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Float.parseFloat(value);
    }

    @Override
    public Double getDoubleValue(String key) {
        String value = get(key);
        return value == null ? null : Double.valueOf(value);
    }

    @Override
    public double getDoubleValue(String key, double defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Double.parseDouble(value);
    }

    @Override
    public Boolean getBooleanValue(String key) {
        String value = get(key);
        return value == null ? null : Boolean.valueOf(value);
    }

    @Override
    public boolean getBooleanValue(String key, boolean defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }

    @Override
    public List<String> getList(String key) {
        return getList(key, String.class);
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz) {
        return getList(key, clazz, new ArrayList<>());
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clazz, List<T> defaultValue) {
        String value = get(key);
        if(StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametrizedType(List.class,List.class,clazz);
            return objectMapper.readValue(value,type);
        } catch (Exception e) {
            /**
             * TODO
             */
            return null;
        }

    }

    @Override
    public Map<String, String> getMap(String key) {
        return getMap(key, String.class);
    }

    @Override
    public <T> Map<String, T> getMap(String key, Class<T> clazz) {
        return getMap(key, clazz, new HashMap<>());
    }

    @Override
    public <T> Map<String, T> getMap(String key, Class<T> clazz, Map<String, T> defaultValue) {
        String value = get(key);
        if(StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametrizedType(Map.class,Map.class,String.class,clazz);
            return objectMapper.readValue(value,type);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T getBean(String key, Class<T> clazz) {
        return getBean(key, clazz, null);
    }

    @Override
    public <T> T getBean(String key, Class<T> clazz, T defaultValue) {
        String value = get(key);
        if(StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        try {
            return objectMapper.readValue(value,clazz);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public String get(String key, String defaultValue, ConfigContext context) {
        key = StringUtils.trimToNull(key);
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        return doGet(key, defaultValue, context);
    }
}
