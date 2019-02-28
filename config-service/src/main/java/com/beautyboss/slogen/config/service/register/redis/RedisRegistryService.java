package com.beautyboss.slogen.config.service.register.redis;

import com.beautyboss.slogen.config.service.enums.RegisterTypeEnum;
import com.beautyboss.slogen.config.service.register.ConfigRegisterService;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public class RedisRegistryService implements ConfigRegisterService {
    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }

    @Override
    public RegisterTypeEnum type() {
        return RegisterTypeEnum.Redis;
    }

    @Override
    public void registerAndPushValue(String key, String value, long timestamp) {

    }

}
