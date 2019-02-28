package com.beautyboss.slogen.config.client.loader;

import com.beautyboss.slogen.config.client.ConfigValue;
import com.beautyboss.slogen.config.client.manager.ConfigContext;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public interface ConfigLoader extends Named,Enabled,ConfigLoaderListenable {

    ConfigValue get(String key, ConfigContext context) throws Exception;
}
