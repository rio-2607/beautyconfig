package com.beautyboss.slogen.config.service.service.impl;

import com.beautyboss.slogen.config.common.utils.LocalCache;
import com.beautyboss.slogen.config.service.consts.ServiceConstants;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.repository.EnvironmentRepository;
import com.beautyboss.slogen.config.service.service.EnvironmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
@Service
public class EnvironmentServiceImpl implements EnvironmentService{

    @Resource
    private EnvironmentRepository environmentRepository;

    private LocalCache<String,List<Environment>> cache = new LocalCache<>(1000,60000);

    @Override
    public List<Environment> queryAll() {
        List<Environment> envs = cache.get(ServiceConstants.ENV_CACHE_KEY);
        if(CollectionUtils.isEmpty(envs)) {
            synchronized (this) {
                envs = cache.get(ServiceConstants.ENV_CACHE_KEY);
                if(CollectionUtils.isEmpty(envs)) {
                    envs = environmentRepository.queryAll();
                    cache.put(ServiceConstants.ENV_CACHE_KEY,envs);
                }
            }
        }
        return envs;
    }

    @Override
    public Environment queryEnvById(long envId) {
        List<Environment> envs = queryAll();
        Map<Long,Environment> envMap = envs.stream().collect(Collectors.toMap(Environment::getId, Function.identity()));
        return envMap.get(envId);
    }

    @Override
    public Map<Integer, List<Environment>> queryEnvMap() {
        List<Environment> allEnv = queryAll();
        return allEnv.stream().collect(Collectors.groupingBy(Environment::getType));
    }

    @Override
    public Long save(Environment env) {
        return environmentRepository.create(env);
    }

    @Override
    public boolean update(Environment env) {
        return environmentRepository.modify(env);
    }

    @Override
    public boolean delete(Integer envId) {
        return environmentRepository.delete(envId);
    }

}
