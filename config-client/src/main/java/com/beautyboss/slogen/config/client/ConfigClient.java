package com.beautyboss.slogen.config.client;

import com.beautyboss.slogen.config.client.exceptions.ConfigException;
import com.beautyboss.slogen.config.client.exceptions.StatusCode;
import com.beautyboss.slogen.config.client.manager.ConfigContext;
import com.beautyboss.slogen.config.client.manager.ConfigManager;

import java.util.List;
import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/17
 */
public class ConfigClient {

    private static ConfigManager configManager = ConfigManagerFactory.getConfigManager();

    public static String getEnvironment() {
        return Environment.getEnv();
    }

    public static String getAppName() {
        return Environment.getAppName();
    }

    public static String get(String key) {
        return configManager.get(key);
    }

    public static String get(String key, String defaultValue) {
        return configManager.get(key, defaultValue);
    }

    public static String get(String key, String defaultValue, ConfigContext context) {
        return configManager.get(key, defaultValue, context);
    }

    public static String getStringValue(String key) {
        return configManager.get(key);
    }

    public static String getStringValue(String key, String defaultValue) {
        return configManager.get(key, defaultValue);
    }

    public static byte getByteValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Byte.parseByte(value);
    }

    public static Byte getByte(String key) {
        return configManager.getByteValue(key);
    }

    public static byte getByteValue(String key, byte defaultValue) {
        return configManager.getByteValue(key, defaultValue);
    }

    public static short getShortValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Short.parseShort(value);
    }

    public static Short getShort(String key) {
        return configManager.getShortValue(key);
    }

    public static short getShortValue(String key, short defaultValue) {
        return configManager.getShortValue(key, defaultValue);
    }

    public static int getIntValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Integer.parseInt(value);
    }

    public static Integer getInt(String key) {
        return configManager.getIntValue(key);
    }

    public static int getIntValue(String key, int defaultValue) {
        return configManager.getIntValue(key, defaultValue);
    }

    public static long getLongValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Long.parseLong(value);
    }

    public static Long getLong(String key) {
        return configManager.getLongValue(key);
    }

    public static long getLongValue(String key, long defaultValue) {
        return configManager.getLongValue(key, defaultValue);
    }

    public static float getFloatValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Float.parseFloat(value);
    }

    public static Float getFloat(String key) {
        return configManager.getFloatValue(key);
    }

    public static float getFloatValue(String key, float defaultValue) {
        return configManager.getFloatValue(key, defaultValue);
    }

    public static double getDoubleValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Double.parseDouble(value);
    }

    public static Double getDouble(String key) {
        return configManager.getDoubleValue(key);
    }

    public static double getDoubleValue(String key, double defaultValue) {
        return configManager.getDoubleValue(key, defaultValue);
    }

    public static boolean getBooleanValue(String key) {
        String value = configManager.get(key);
        if (value == null) {
            throw new ConfigException(StatusCode.ERROR_CONFIG_NOT_EXIST,key);
        }
        return Boolean.parseBoolean(value);
    }

    public static Boolean getBoolean(String key) {
        return configManager.getBooleanValue(key);
    }

    public static boolean getBooleanValue(String key, boolean defaultValue) {
        return configManager.getBooleanValue(key, defaultValue);
    }

    public static List<String> getList(String key) {
        return configManager.getList(key);
    }

    public static <T> List<T> getList(String key, Class<T> clazz) {
        return configManager.getList(key, clazz);
    }

    public static <T> List<T> getList(String key, Class<T> clazz, List<T> defaultValue) {
        return configManager.getList(key, clazz, defaultValue);
    }

    public static Map<String, String> getMap(String key) {
        return configManager.getMap(key);
    }

    public static <T> Map<String, T> getMap(String key, Class<T> clazz) {
        return configManager.getMap(key, clazz);
    }

    public static <T> Map<String, T> getMap(String key, Class<T> clazz,
                                            Map<String, T> defaultValue) {
        return configManager.getMap(key, clazz, defaultValue);
    }

    public static <T> T getBean(String key, Class<T> clazz) {
        return configManager.getBean(key, clazz);
    }

    public static <T> T getBean(String key, Class<T> clazz, T defaultValue) {
        return configManager.getBean(key, clazz, defaultValue);
    }


}
