package com.beautyboss.slogen.config.service.consts;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
public class ServiceConstants {

    public static final String ENV_CACHE_KEY = "env_cache";

    public static final String CONFIG_CACHE_KEY = "config_cache_key_";

    public static String buildConfigKey(Long configId) {
        return CONFIG_CACHE_KEY + configId;
    }

}
