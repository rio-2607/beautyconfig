package com.beautyboss.slogen.config.resource.mapper;

import com.beautyboss.slogen.config.resource.entity.GroupEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface GroupMapper {

    Long add(GroupEntity entity);

    int delete(@Param("id") Long id);

    GroupEntity query(@Param("id") Long id);

    List<GroupEntity> queryByProject(@Param("projectId")Long projectId);

    int update(GroupEntity entity);
}
