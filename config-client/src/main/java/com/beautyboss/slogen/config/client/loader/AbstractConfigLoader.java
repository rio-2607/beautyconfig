package com.beautyboss.slogen.config.client.loader;


import com.beautyboss.slogen.config.client.ConfigEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public abstract class AbstractConfigLoader implements ConfigLoader {

    protected static final Object VALUE = new Object();
    protected ConcurrentMap<ConfigLoaderListener, Object> configLoaderListeners = new ConcurrentHashMap<ConfigLoaderListener, Object>();
    protected boolean enabled = true;

    @Override
    public void addConfigLoaderListener(ConfigLoaderListener configLoaderListener) {
        configLoaderListeners.put(configLoaderListener, VALUE);
    }

    @Override
    public void removeConfigLoaderListener(ConfigLoaderListener configLoaderListener) {
        configLoaderListeners.remove(configLoaderListener);
    }

    @Override
    public void fireConfigChanged(ConfigEvent configEvent) {
        for (ConfigLoaderListener cll : configLoaderListeners.keySet()) {
            cll.configChanged(this, configEvent);
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return getName();
    }
}
