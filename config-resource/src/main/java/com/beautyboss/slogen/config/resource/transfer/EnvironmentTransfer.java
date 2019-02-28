package com.beautyboss.slogen.config.resource.transfer;

import com.beautyboss.slogen.config.resource.entity.EnvironmentEntity;
import com.beautyboss.slogen.config.service.domain.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public class EnvironmentTransfer extends Transfer {

    public static Environment transfer2DO(EnvironmentEntity entity) {
        Environment environment = transfer(entity,Environment.class);
        List<String> ips = Arrays.stream(entity.getIps().split(",")).collect(Collectors.toList());
        environment.setIps(ips);
        return environment;
    }

    public static EnvironmentEntity transfer2Entity(Environment environment) {
        EnvironmentEntity entity = transfer2Entity(environment,EnvironmentEntity.class);
        String ips = environment.getIps().stream().collect(Collectors.joining(","));
        entity.setIps(ips);
        return entity;
    }


}
