package com.beautyboss.slogen.config.client.util;

import com.beautyboss.slogen.config.client.ConfigEvent;
import com.beautyboss.slogen.config.client.ConfigEventType;
import com.beautyboss.slogen.config.client.ConfigValue;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class ConfigUtils {


    public static ConfigEvent configChanged(String key, ConfigValue wc, ConfigValue owc, boolean fallback) {
        if (wc == owc || equals(wc, owc)) {
            // nothing changed
            return null;
        } else {
            if (fallback) {
                return new ConfigEvent(key, ConfigEventType.CONFIG_FALL_BACK, wc, owc);
            } else {
                return new ConfigEvent(key, wc, owc);
            }
        }
    }

    public static boolean equals(ConfigValue wc, ConfigValue owc) {
        if (isNullConfig(wc)) {
            return isNullConfig(owc);
        } else {
            if (isNullConfig(owc))
                return false;
            else
                return wc.getValue().equals(owc.getValue());
        }
    }

    public static boolean isNullConfig(ConfigValue config) {
        return config == null || config.getValue() == null;
    }
}
