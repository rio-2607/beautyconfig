package com.beautyboss.slogen.config.service.service;

import com.beautyboss.slogen.config.service.domain.Config;
import com.beautyboss.slogen.config.service.domain.ConfigInstance;
import com.beautyboss.slogen.config.service.domain.Environment;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
public interface ConfigService {

    long createConfig(Config config);

    Config queryConfig(long configId);

    long createConfigInstance(ConfigInstance instance);

    boolean updateConfig(Config config);

    boolean updateConfigInstance(ConfigInstance instance);

    boolean deleteConfig(Long configId);

    boolean deleteConfigInstance(Long configId);

    Config query(Config config, Environment environment);

    void registerAndPush(ConfigInstance instance);

    List<ConfigInstance> queryInstance(Long configId);

    ConfigInstance queryInstance(Long configId,Integer envId);

    List<Config> queryConfig(Long projectId,Integer groupId);

}
