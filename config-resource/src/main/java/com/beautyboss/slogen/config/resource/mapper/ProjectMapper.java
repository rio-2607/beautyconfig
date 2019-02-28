package com.beautyboss.slogen.config.resource.mapper;

import com.beautyboss.slogen.config.resource.entity.ProjectEntity;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface ProjectMapper {

    long create(ProjectEntity entity);

    int modify(ProjectEntity entity);

    ProjectEntity query(long id);

    int delete(long id);
}
