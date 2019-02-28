package com.beautyboss.slogen.config.service.repository;

import com.beautyboss.slogen.config.service.domain.Config;
import com.beautyboss.slogen.config.service.domain.ConfigInstance;
import com.beautyboss.slogen.config.service.domain.Environment;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface ConfigRepository {

    long createConfig(Config config);

    long createConfigInstance(ConfigInstance instance);

    boolean updateConfig(Config config);

    boolean updateConfigInstance(ConfigInstance instance);

    boolean deleteConfig(Long configId);

    boolean deleteConfigInstance(Long instanceId);

    Config query(Config config, Environment environment);

    Config queryByKey(Long projectId,Long groupId,String key);

    Config queryById(Long id);

    List<ConfigInstance> queryInstanceByConfigId(Long configId);

    ConfigInstance queryInstance(Long configId,Integer envId);

    List<Config> query(Long projectId,Integer groupId);


}
