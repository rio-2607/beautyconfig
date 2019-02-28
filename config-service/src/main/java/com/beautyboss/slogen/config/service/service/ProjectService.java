package com.beautyboss.slogen.config.service.service;


import com.beautyboss.slogen.config.service.domain.Project;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface ProjectService {

    Long create(Project project);

    Project query(Long projectId);

    boolean modify(Project project);

    boolean delete(Long projectId);

}
