package com.beautyboss.slogen.config.resource.mapper;

import com.beautyboss.slogen.config.resource.entity.ConfigInstanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface ConfigInstanceMapper {

    Long create(ConfigInstanceEntity entity);

    int delete(@Param("id") Long id);

    List<ConfigInstanceEntity> query(@Param("configId") Long configId);

    ConfigInstanceEntity queryByEnv(@Param("configId")Long configId,@Param("envId") Integer envId);

    int modify(ConfigInstanceEntity entity);

}
