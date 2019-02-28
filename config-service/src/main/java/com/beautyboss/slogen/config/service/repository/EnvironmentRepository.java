package com.beautyboss.slogen.config.service.repository;

import com.beautyboss.slogen.config.service.domain.Environment;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface EnvironmentRepository {

    Long create(Environment environment);

    Environment query(Integer id);

    boolean modify(Environment environment);

    boolean delete(Integer id);

    List<Environment> queryAll();

}
