package com.beautyboss.slogen.config.service.register.zookeeper;

import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.register.ConfigRegisterService;
import com.beautyboss.slogen.config.service.register.ConfigRegisterServiceFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
@Service("zookeeper")
public class ZookeeperRegistryServiceFactory implements ConfigRegisterServiceFactory {

    @Override
    public ConfigRegisterService createRegisterService(Environment environment) {
        try {
            String ips = environment.getIps().stream().collect(Collectors.joining(","));
            ZookeeperRegistryService service = new ZookeeperRegistryService(ips);
            service.init();
            return service;
        } catch (Exception e) {
        }
        return null;
    }

}
