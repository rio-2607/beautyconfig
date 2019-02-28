package com.beautyboss.slogen.config.resource.mapper;

import com.beautyboss.slogen.config.resource.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface ConfigMapper {

    Long create(ConfigEntity entity);

    ConfigEntity queryById(@Param("id") Long id);

    int modify(ConfigEntity entity);

    List<ConfigEntity> queryByProject(@Param("projectId") Long projectId);

    List<ConfigEntity> queryBy(@Param("projectId") Long projectId,@Param("groupId") Integer groupId);

    ConfigEntity queryByKey(@Param("projectId") Long projectId, @Param("groupId") Long groupId, @Param("key") String key);

    int deleteConfig(@Param("id") Long configId);


}
