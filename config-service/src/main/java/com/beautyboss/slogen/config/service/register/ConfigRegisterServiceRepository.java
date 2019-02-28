package com.beautyboss.slogen.config.service.register;

import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.enums.RegisterTypeEnum;
import com.beautyboss.slogen.config.service.service.EnvironmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
@Service
public class ConfigRegisterServiceRepository {

    @Resource
    private ConfigRegisterServiceManager configRegisterServiceManager;

    @Resource
    private EnvironmentService environmentService;

    private static ConcurrentMap<Integer, List<ConfigRegisterService>> registerServices = new ConcurrentHashMap();


    public List<ConfigRegisterService> getRegisterService(int envId) {
        List<ConfigRegisterService> services = registerServices.get(envId);
        if(CollectionUtils.isNotEmpty(services)) {
            return services;
        }
        Environment environment = environmentService.queryEnvById(envId);
        services = configRegisterServiceManager.createConfigRegisterService(environment);
        if(CollectionUtils.isEmpty(services)) {
            throw new ConfigException();
        }
        List<ConfigRegisterService> oldServices = registerServices.putIfAbsent(envId,services);
        destoryRegisterService(oldServices,services);
        return services;
    }

    private void destoryRegisterService(List<ConfigRegisterService> oldServices,List<ConfigRegisterService> services) {
        /**
         * TODO
         */
        try {

            Map<RegisterTypeEnum,List<ConfigRegisterService>> oldTypeMap = oldServices.stream().collect(Collectors.groupingBy(ConfigRegisterService::type));
            Map<RegisterTypeEnum,List<ConfigRegisterService>> typeMap = services.stream().collect(Collectors.groupingBy(ConfigRegisterService::type));


        } catch (Exception e) {

        }
    }

}
