package com.beautyboss.slogen.config.client.loader.zookeeper;

import com.beautyboss.slogen.config.client.loader.AbstractConfigLoaderFactory;
import com.beautyboss.slogen.config.client.loader.ConfigLoader;
import com.beautyboss.slogen.config.client.util.PropertiesUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class ZookeeperConfigloaderFactory extends AbstractConfigLoaderFactory {

    private static final String KEY_ZOOKEEPER_ADDRESS = "zookeeperAddress";
    private static final String KEY_ZOOKEEPER_ACCOUNT = "zookeeperAccount";
    private static final String KEY_ZOOKEEPER_PASSWORD = "zookeeperPassword";

    private static Map<String, ConfigLoader> configLoaders = new ConcurrentHashMap<String, ConfigLoader>();

    @Override
    public ConfigLoader getConfigLoader(Map<String, String> loaderConfigs) {
        String zookeeperAddress = PropertiesUtils.get(loaderConfigs,
                KEY_ZOOKEEPER_ADDRESS, "");
        String zookeeperAccount = PropertiesUtils.get(loaderConfigs,
                KEY_ZOOKEEPER_ACCOUNT, null);
        String zookeeperPassword = PropertiesUtils.get(loaderConfigs,
                KEY_ZOOKEEPER_PASSWORD, null);
        ConfigLoader cl = getConfigLoader(zookeeperAddress, zookeeperAccount, zookeeperPassword);
        return cl;
    }

    public static synchronized ConfigLoader getConfigLoader(String zkAddress,String zkAccount,String zkPassword) {
        String key = getKey(zkAddress, zkAccount, zkPassword);
        ConfigLoader loader = configLoaders.get(key);
        if(null == loader) {
            loader = new ZookeeperConfigLoader(zkAddress,zkAccount,zkPassword);
            configLoaders.put(key,loader);
        }
        return loader;
    }

    private static String getKey(String zkAddress,String zkAccount,String zkPassword) {
        StringBuilder builder = new StringBuilder(128);
        builder.append(zkAddress).append("|").append(zkAccount).append("|").append(zkPassword);
        return builder.toString();
    }
}
