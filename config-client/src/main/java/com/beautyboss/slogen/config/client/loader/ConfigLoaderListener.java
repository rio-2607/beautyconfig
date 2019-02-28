package com.beautyboss.slogen.config.client.loader;


import com.beautyboss.slogen.config.client.ConfigEvent;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public interface ConfigLoaderListener {

    void stateChanged(ConfigLoader configLoader, int state);

    void configChanged(ConfigLoader configLoader, ConfigEvent configEvent);
}
