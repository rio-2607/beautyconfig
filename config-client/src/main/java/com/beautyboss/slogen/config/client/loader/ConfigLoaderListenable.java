package com.beautyboss.slogen.config.client.loader;


import com.beautyboss.slogen.config.client.ConfigEvent;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public interface ConfigLoaderListenable {

    void addConfigLoaderListener(ConfigLoaderListener configLoaderListener);

    void removeConfigLoaderListener(ConfigLoaderListener configLoaderListener);

    void fireConfigChanged(ConfigEvent configEvent);

}
