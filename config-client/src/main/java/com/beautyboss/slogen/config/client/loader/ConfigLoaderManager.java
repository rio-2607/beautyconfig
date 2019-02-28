package com.beautyboss.slogen.config.client.loader;

import com.beautyboss.slogen.config.client.ConfigValue;
import com.beautyboss.slogen.config.client.manager.ConfigContext;
import com.beautyboss.slogen.config.client.util.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
@Slf4j
public class ConfigLoaderManager {

    private static List<ConfigLoaderFactory> configLoaderFactories = ExtensionLoader.getExtensionList(ConfigLoaderFactory.class);

    private ConfigLoaderListener configManager;

    private Map<String,String> loaderConfigs;

    private List<ConfigLoader> configLoaders;

    private ConfigLoader lastConfigLoader;

    public ConfigLoaderManager(ConfigLoaderListener configManager,Map<String,String> loaderConfigs) {
        this.configManager = configManager;
        this.loaderConfigs = loaderConfigs;
        if(null == this.loaderConfigs) {
            this.loaderConfigs = Collections.emptyMap();
        }
        this.configLoaders = init(loaderConfigs);
    }

    public List<ConfigLoader> init(Map<String,String> loaderConfigs) {
        List<ConfigLoader> configLoaders = initConfigLoaders(configLoaderFactories,loaderConfigs);
        lastConfigLoader = configLoaders.get(0);
        return configLoaders;
    }

    private List<ConfigLoader> initConfigLoaders(List<ConfigLoaderFactory> configLoaderFactories, Map<String,String> loaderConfigs) {
        return configLoaderFactories.stream().map(factory -> {
            try {
                ConfigLoader loader = factory.getConfigLoader(loaderConfigs);
                if(null != loader) {
                    loader.addConfigLoaderListener(configManager);
                }
                return loader;
            } catch (Exception e) {
                return null;
            }
        }).collect(Collectors.toList());
    }

    public ConfigValue get(String key, ConfigContext context) throws Exception {
        ConfigValue value = null;
        for(ConfigLoader configLoader : configLoaders) {
            try {
                if(!configLoader.isEnabled()) {
                    continue;
                }
                value = configLoader.get(key,context);
                if(null == value) {
                    continue;
                }
                value.setSource(configLoader.getName());
            } catch (Exception e) {
                if(configLoader == lastConfigLoader) {
                    throw e;
                }
            }
        }

        if(null == value) {
            value = new ConfigValue(key,null,null);
        }
        value.setContext(context);

        if(null != value.getValue()) {

        } else {

        }
        return value;
    }
}
