package com.beautyboss.slogen.config.client;

import com.beautyboss.slogen.config.client.manager.ConfigManager;
import com.beautyboss.slogen.config.client.manager.ConfigManagerImpl;
import com.beautyboss.slogen.config.client.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
@Slf4j
public class ConfigManagerFactory {

    private static final String ACTIVE_ENV_KEY = "spring.profiles.active";
    private static Map<String, String> configProperties;
    static {
        configProperties = loadProperties();
    }

    public static ConfigManager getConfigManager() {
        return new ConfigManagerImpl(configProperties);
    }

    private static Map<String, String> loadProperties() {
        Map<String, String> properties = new HashMap<>();
        loadProperties("application.properties",properties);
        if(properties.containsKey(ACTIVE_ENV_KEY)) {
            String env = properties.get(ACTIVE_ENV_KEY);
            loadProperties("application-"+env+".properties",properties);
        }

        for (String key : properties.keySet()) {
            log.info(">>>>>>>>>>>" + key + ": " + properties.get(key));
        }
        return properties;
    }

    private static void loadProperties(String fileName,Map<String,String> container) {
        try {
            Properties props = PropertiesUtils.loadFromClassPath(fileName);
            if (props != null) {
                for (String key : props.stringPropertyNames()) {
                    container.put(key, props.getProperty(key));
                }
            }
        } catch (Exception e) {
        }
    }

}
