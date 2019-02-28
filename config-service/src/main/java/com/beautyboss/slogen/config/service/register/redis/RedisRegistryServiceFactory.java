package com.beautyboss.slogen.config.service.register.redis;

import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.register.ConfigRegisterService;
import com.beautyboss.slogen.config.service.register.ConfigRegisterServiceFactory;
import org.springframework.stereotype.Service;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
@Service("redis")
public class RedisRegistryServiceFactory implements ConfigRegisterServiceFactory{

    @Override
    public ConfigRegisterService createRegisterService(Environment environment) {
        return null;
    }
}
