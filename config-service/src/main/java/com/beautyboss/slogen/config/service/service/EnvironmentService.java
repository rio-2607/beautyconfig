package com.beautyboss.slogen.config.service.service;

import com.beautyboss.slogen.config.service.domain.Environment;

import java.util.List;
import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
public interface EnvironmentService {

    List<Environment> queryAll();

    Environment queryEnvById(long envId);

    Map<Integer,List<Environment>> queryEnvMap();

    Long save(Environment env);

    boolean update(Environment env);

    boolean delete(Integer envId);


}
