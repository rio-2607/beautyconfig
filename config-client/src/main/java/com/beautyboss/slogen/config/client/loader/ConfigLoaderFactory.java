package com.beautyboss.slogen.config.client.loader;

import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public interface ConfigLoaderFactory {

    ConfigLoader getConfigLoader(Map<String,String> loaderConfigs);

}
