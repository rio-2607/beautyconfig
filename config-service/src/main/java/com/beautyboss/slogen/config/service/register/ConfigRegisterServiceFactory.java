package com.beautyboss.slogen.config.service.register;

import com.beautyboss.slogen.config.service.domain.Environment;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
public interface ConfigRegisterServiceFactory {

    ConfigRegisterService createRegisterService(Environment environment);


}
