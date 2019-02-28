package com.beautyboss.slogen.config.client.manager;

import com.beautyboss.slogen.config.client.*;
import com.beautyboss.slogen.config.client.loader.ConfigLoader;
import com.beautyboss.slogen.config.client.loader.ConfigLoaderListener;
import com.beautyboss.slogen.config.client.loader.ConfigLoaderManager;
import com.beautyboss.slogen.config.client.util.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Slf4j
public class ConfigManagerImpl extends AbstractConfigManager implements ConfigLoaderListener {

    private static final String KEY_USE_CACHE = "useCache";
    private static final boolean DEFAULT_USE_CACHE = true;
    private static final int MAX_CONFIGS = 2000;

    private ConfigContext defaultContext;
    private String persistDir = "./config";
    private boolean enablePersist = true;

    private ConfigLoaderManager configLoaderManager;

    private Map<String, ConfigValue> loadedConfig = Collections
            .synchronizedMap(new LinkedHashMap<String, ConfigValue>(256, 0.75f, true) {
                private static final long serialVersionUID = 1L;

                @Override
                public boolean removeEldestEntry(Map.Entry<String, ConfigValue> eldest) {
                    return size() > MAX_CONFIGS;
                }
            });

    private ConcurrentMap<String, ConfigFuture> configFutures = new ConcurrentHashMap<>();

    public ConfigManagerImpl(Map<String, String> properties) {
        initPersistInfo();
        defaultContext = createConfigContext(properties);
        configLoaderManager = new ConfigLoaderManager(this, properties);
        logStartupInfo();
    }

    private void initPersistInfo() {
        enablePersist = FileUtils.ensureAccess(persistDir);
    }

    private ConfigContext createConfigContext(Map<String, String> properties) {
        boolean useCache = PropertiesUtils.getBoolean(properties, KEY_USE_CACHE, DEFAULT_USE_CACHE);
        return new ConfigContext(useCache);
    }

    private void logStartupInfo() {
    }

    @Override
    protected String doGet(String key, String defaultValue, ConfigContext context) {
        ConfigValue configValue = null;
        context = (context == null ? defaultContext : context);
        if (context.isUseCache()) {
            configValue = readCache(key);
        }

        if (configValue == null) {
            ConfigFuture existingFuture;
            ConfigFuture future = new ConfigFuture(key);
            existingFuture = configFutures.putIfAbsent(key, future);
            if (existingFuture != null) {
                try {
                    configValue = existingFuture.get();
                } catch (ExecutionException e) {
                    log.error("get config error",e);
                } catch (Exception e) {
                    log.error("get config error",e);
                }
            } else {
                try {
                    configValue = configLoaderManager.get(key, context);
                    future.setResult(configValue);
                    if (configValue != null && context.isUseCache()) {
                        writeCache(key, configValue, true);
                    }
                } catch (Exception e) {
                    future.setResult(e);
                } finally {
                    configFutures.remove(key);
                }
            }
        }

        return configValue.getValue() == null ? defaultValue : configValue.getValue();
    }

    private void writeCache(String key, ConfigValue newConfig, boolean isForce) {
        if (!isForce && ConfigUtils.isNullConfig(newConfig)) {
            loadedConfig.remove(key);
            if (enablePersist) {
                TaskManager.submit(new PersistTask(key, null));
            }
        } else {
            ConfigValue oldConfig = loadedConfig.put(key, newConfig);
            if (needPersist(newConfig, oldConfig)) {
                TaskManager.submit(new PersistTask(key, newConfig.getValue()));
            }
        }
    }

    private boolean needPersist(ConfigValue newConfig, ConfigValue oldConfig) {
        return enablePersist &&
                !ConfigUtils.equals(newConfig, oldConfig) &&
                !newConfig.isLastWorkingConfig();
    }

    private class PersistTask implements Runnable {

        private String key;
        private String value;
        private String file;

        public PersistTask(String key, String value) {
            this.key = key;
            this.value = value;
            this.file = persistDir + "/" + key;
        }

        @Override
        public void run() {
            try {
                if (value == null) {
                    FileUtils.delete(file);
                } else {
                    FileUtils.write(file, value);
                }
            } catch (Exception e) {
            }
        }

    }

    private ConfigValue readCache(String key) {
        return loadedConfig.get(key);
    }

    @Override
    public void stateChanged(ConfigLoader configLoader, int state) {
        syncConfigs();
    }

    @Override
    public void configChanged(ConfigLoader configLoader, ConfigEvent configEvent) {
        ConfigValue oldValue = loadedConfig.get(configEvent.getKey());
        ConfigValue newValue = configEvent.getConfigValue();
        configEvent.setOldConfigValue(oldValue);
        if (ConfigUtils.isNullConfig(newValue)) {
            if (!ConfigUtils.isNullConfig(oldValue)) {
                // config deleted
                doConfigChanged(configLoader, configEvent);
            }
        } else {
            if (ConfigUtils.isNullConfig(oldValue)) {
                // config added
                doConfigChanged(configLoader, configEvent);
            } else {
                // config changed
                if (ConfigEventType.CONFIG_FALL_BACK.equals(configEvent.getType())
                        || newValue.getModifyTime() > oldValue.getModifyTime()) {
                    doConfigChanged(configLoader, configEvent);
                }
            }
        }
    }

    private void doConfigChanged(ConfigLoader configLoader, ConfigEvent configEvent) {
        enrichSource(configLoader, configEvent.getConfigValue());
        writeCache(configEvent.getKey(), configEvent.getConfigValue(), false);
    }


    private void enrichSource(ConfigLoader configLoader, ConfigValue configValue) {
        if (configValue != null && configLoader != null) {
            configValue.setSource(configLoader.getName());
        }
    }

    private void syncConfigs() {
        List<ConfigValue> configs = new LinkedList<ConfigValue>(loadedConfig.values());
        for (ConfigValue cv : configs) {
            try {
                ConfigContext context = (cv.getContext() == null ? defaultContext
                        : cv.getContext());
                ConfigValue ncv = configLoaderManager.get(cv.getKey(), context);
                if (!StringUtils.equals(cv.getValue(), ncv.getValue())) {
                    ConfigEvent ce = new ConfigEvent(cv.getKey(), ncv, cv);
//                    logger.info(">>>>>>config changed: " + ce);
                    configChanged(null, ce);
                }
            } catch (Exception e) {
//                logger.error("failed to sync " + cv, e);
            }
        }
    }


}
