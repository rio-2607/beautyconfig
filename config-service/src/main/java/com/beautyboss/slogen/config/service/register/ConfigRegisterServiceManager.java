package com.beautyboss.slogen.config.service.register;

import com.beautyboss.slogen.config.service.domain.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Service
public class ConfigRegisterServiceManager {

    @Resource
    private List<ConfigRegisterServiceFactory> configRegisterServiceFactories;

    public List<ConfigRegisterService> createConfigRegisterService(Environment environment) {
        return configRegisterServiceFactories.stream()
                .map(fac -> fac.createRegisterService(environment))
                .filter(o -> null != o)
                .collect(Collectors.toList());
    }


}
