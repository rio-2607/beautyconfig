package com.beautyboss.slogen.config.service.service.impl;

import com.beautyboss.slogen.config.common.enums.StatusCode;
import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import com.beautyboss.slogen.config.common.utils.LocalCache;
import com.beautyboss.slogen.config.common.utils.LocalCacheFactory;
import com.beautyboss.slogen.config.service.consts.ServiceConstants;
import com.beautyboss.slogen.config.service.domain.Config;
import com.beautyboss.slogen.config.service.domain.ConfigInstance;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.register.ConfigRegisterService;
import com.beautyboss.slogen.config.service.register.ConfigRegisterServiceRepository;
import com.beautyboss.slogen.config.service.repository.ConfigRepository;
import com.beautyboss.slogen.config.service.service.ConfigService;
import com.beautyboss.slogen.config.service.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigRepository configRepository;

    @Resource
    private ConfigRegisterServiceRepository configRegisterServiceRepository;

    private LocalCache<String,Config> cache = LocalCacheFactory.createStringKeyCache(null,null);


    public long createConfig(Config config) {
        Config configFound = configRepository.queryByKey(config.getProjectId(),config.getGroupId(),config.getKey());
        if(null != configFound) {
            throw new ConfigException(StatusCode.CONFIG_EXIST,config.getKey());
        }

        Long currentUserId = SecurityUtils.getCurrentUserId();

        if(null == currentUserId) {
            throw new ConfigException();
        }
        if(null == config.getCreateUserId()) {
            config.setCreateUserId(currentUserId);
        }
        if(null == config.getModifyUserId()) {
            config.setModifyUserId(currentUserId);
        }
        if(null == config.getCreateTime()) {
            config.setCreateTime(new Date());
        }
        if(null == config.getModifyTime()) {
            config.setModifyTime(new Date());
        }
        config.setSeq(0); // 新建的key的序列号始终为0

        Long configId = configRepository.createConfig(config);
        config.setId(configId);

        /**
         * 添加日志记录
         */
        return configId;
    }

    @Override
    public Config queryConfig(long configId) {
        Config config = cache.get(ServiceConstants.buildConfigKey(configId));
        if(null == config) {
            config = configRepository.queryById(configId);
            if(null != config) {
                cache.put(ServiceConstants.buildConfigKey(configId),config);
            }

        }
        return config;
    }

    @Override
    public long createConfigInstance(ConfigInstance instance) {
        Long userId = SecurityUtils.getCurrentUserId();
        if(null == instance.getCreateUserId()) {
            instance.setCreateUserId(userId);
        }
        if(null == instance.getModifyUserId()) {
            instance.setModifyUserId(userId);
        }
        // 要先保存到数据库中，然后在推送到redis和zk上面去
        long instanceId = configRepository.createConfigInstance(instance);
        registerAndPush(instance);

        // 推送到refister
        return instanceId;
    }



    public boolean updateConfig(Config config) {
        return configRepository.updateConfig(config);
    }

    public boolean updateConfigInstance(ConfigInstance instance) {
        boolean result = configRepository.updateConfigInstance(instance);
        registerAndPush(instance);
        return result;
    }

    public boolean deleteConfig(Long configId) {
        return configRepository.deleteConfig(configId);
    }

    public boolean deleteConfigInstance(Long configId) {
        return configRepository.deleteConfigInstance(configId);
    }

    public Config query(Config config, Environment environment) {
        return null;
    }

    @Override
    public void registerAndPush(ConfigInstance instance) {
        Config config = queryConfig(instance.getConfigId());
        List<ConfigRegisterService> services = configRegisterServiceRepository.getRegisterService(instance.getEnvId());

        services.forEach(service -> service.registerAndPushValue(config.getKey(),instance.getValue(),new Date().getTime()));
    }

    @Override
    public List<ConfigInstance> queryInstance(Long configId) {
        return configRepository.queryInstanceByConfigId(configId);
    }

    @Override
    public ConfigInstance queryInstance(Long configId, Integer envId) {
        return configRepository.queryInstance(configId, envId);
    }

    @Override
    public List<Config> queryConfig(Long projectId, Integer groupId) {
        return configRepository.query(projectId, groupId);
    }

}
