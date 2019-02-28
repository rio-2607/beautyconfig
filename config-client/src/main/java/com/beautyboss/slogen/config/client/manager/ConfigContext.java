package com.beautyboss.slogen.config.client.manager;

import com.beautyboss.slogen.config.client.Constants;
import lombok.Data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Data
public class ConfigContext {

    private static final boolean DEFAULT_USE_CACHE = true;

    public static final ConfigContext DEFAULT_CONTEXT = new ConfigContext();

    private boolean useCache = true;

    public ConfigContext() {
        this( DEFAULT_USE_CACHE);
    }

    public ConfigContext( boolean useCache) {
        this.useCache = useCache;
    }

}
