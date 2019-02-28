package com.beautyboss.slogen.config.resource.mapper;

import com.beautyboss.slogen.config.resource.entity.EnvironmentEntity;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface EnvironmentMapper {

    Integer create(EnvironmentEntity entity);

    EnvironmentEntity query(Integer id);

    int modify(EnvironmentEntity entity);

    int delete(Integer id);

    List<EnvironmentEntity> queryAll();

}
