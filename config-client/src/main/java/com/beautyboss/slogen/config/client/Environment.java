package com.beautyboss.slogen.config.client;

import com.beautyboss.slogen.config.client.util.PropertiesUtils;
import com.beautyboss.slogen.config.client.util.StringUtils;

import java.util.Properties;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class Environment {


    private static Properties props;

    private static String appName;

    private static String env;

    static {
        try {
            props = PropertiesUtils.loadFromClassPath("application.properties");
        } catch (Throwable t) {

        }
        if(null != props) {
            appName = get("app.name");
            env = get("config.env");
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getAppName() {
        return appName;
    }

    private static String get(String key) {
        String value = props.getProperty(key);
        return StringUtils.trim(value);
    }

    public static String getEnv() {
        return env;
    }
}
