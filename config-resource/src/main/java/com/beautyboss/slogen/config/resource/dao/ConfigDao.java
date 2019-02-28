package com.beautyboss.slogen.config.resource.dao;

import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import com.beautyboss.slogen.config.resource.entity.ConfigEntity;
import com.beautyboss.slogen.config.resource.entity.ConfigInstanceEntity;
import com.beautyboss.slogen.config.resource.mapper.ConfigInstanceMapper;
import com.beautyboss.slogen.config.resource.mapper.ConfigMapper;
import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Config;
import com.beautyboss.slogen.config.service.domain.ConfigInstance;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.repository.ConfigRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Repository
public class ConfigDao implements ConfigRepository {

    @Resource
    private ConfigMapper configMapper;

    @Resource
    private ConfigInstanceMapper configInstanceMapper;

    @Override
    public long createConfig(Config config) {
        ConfigEntity entity = Transfer.transfer2Entity(config,ConfigEntity.class);
        configMapper.create(entity);
        return entity.getId();
    }

    @Override
    public long createConfigInstance(ConfigInstance instance) {
        ConfigInstanceEntity entity = Transfer.transfer(instance,ConfigInstanceEntity.class);
        configInstanceMapper.create(entity);
        return entity.getId();
    }

    @Override
    public boolean updateConfig(Config config) {
        ConfigEntity entity = Transfer.transfer(config,ConfigEntity.class);
        return 1 == configMapper.modify(entity);
    }

    @Override
    public boolean updateConfigInstance(ConfigInstance instance) {
        ConfigInstanceEntity entity = Transfer.transfer(instance,ConfigInstanceEntity.class);
        return 1 == configInstanceMapper.modify(entity);
    }

    @Override
    public boolean deleteConfig(Long configId) {
        return 1 == configMapper.deleteConfig(configId);
    }

    @Override
    public boolean deleteConfigInstance(Long instanceId) {
        return 1 == configInstanceMapper.delete(instanceId);
    }

    @Override
    public Config query(Config config, Environment environment) {
        return null;
    }

    @Override
    public Config queryByKey(Long projectId, Long groupId, String key) {
        ConfigEntity entity = configMapper.queryByKey(projectId,groupId,key);
        try {
            return Transfer.transfer(entity,Config.class);
        } catch (ConfigException e) {
            return null;
        }
    }

    @Override
    public Config queryById(Long id) {
        ConfigEntity entity = configMapper.queryById(id);
        try {
            return Transfer.transfer(entity,Config.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConfigInstance> queryInstanceByConfigId(Long configId) {
        List<ConfigInstanceEntity> entities = configInstanceMapper.query(configId);
        return entities.stream().map(e -> Transfer.transfer(e,ConfigInstance.class)).collect(Collectors.toList());
    }

    @Override
    public ConfigInstance queryInstance(Long configId, Integer envId) {
        ConfigInstanceEntity entity = configInstanceMapper.queryByEnv(configId,envId);
        return Transfer.transfer(entity,ConfigInstance.class);
    }

    @Override
    public List<Config> query(Long projectId, Integer groupId) {
        List<ConfigEntity> entities = configMapper.queryBy(projectId,groupId);
        return entities.stream()
                .map(e -> Transfer.transfer(e,Config.class))
                .collect(Collectors.toList());
    }

}
