package com.beautyboss.slogen.config.client.util;

import com.beautyboss.slogen.config.client.Constants;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class ZookeeperUtils {

    private static final String PATH_SEPARATOR = "/";

    private static final String CONFIG_TIMESTAMP = "TIMESTAMP";

    private static final String CONTEXTNODE = "CONTEXTNODE";

    public static String getConfigPath(String key) {
        String path = Constants.CONFIG_PATH + PATH_SEPARATOR + key + "/" + CONTEXTNODE;
        return path;
    }

    public static String getConfigPath(String key, String group) {
        String path = Constants.CONFIG_PATH + PATH_SEPARATOR + key;
        if (group != null)
            path = path + PATH_SEPARATOR + group;
        return path;
    }

    public static String getTimestampPath(String path) {
        return path + PATH_SEPARATOR + CONFIG_TIMESTAMP;
    }

}
