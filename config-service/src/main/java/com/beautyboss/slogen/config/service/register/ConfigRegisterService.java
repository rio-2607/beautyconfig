package com.beautyboss.slogen.config.service.register;

import com.beautyboss.slogen.config.service.enums.RegisterTypeEnum;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
public interface ConfigRegisterService {

    void init();

    void destory();

    RegisterTypeEnum type();

    void registerAndPushValue(final String key,final String value,long timestamp);

}
