package com.beautyboss.slogen.config.resource.dao;

import com.beautyboss.slogen.config.resource.entity.ProjectEntity;
import com.beautyboss.slogen.config.resource.mapper.ProjectMapper;
import com.beautyboss.slogen.config.resource.transfer.ProjectTransfer;
import com.beautyboss.slogen.config.service.domain.Project;
import com.beautyboss.slogen.config.service.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Repository
public class ProjectDao implements ProjectRepository {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public Long create(Project project) {
        ProjectEntity entity = ProjectTransfer.transfer2Entity(project);
        projectMapper.create(entity);
        return entity.getId();
    }

    public Project query(Long projectId) {
        ProjectEntity entity = projectMapper.query(projectId);
        return ProjectTransfer.transfer2DO(entity);
    }

    @Override
    public boolean modify(Project project) {
        ProjectEntity entity = ProjectTransfer.transfer2Entity(project);
        return 1 == projectMapper.modify(entity);
    }

    @Override
    public boolean delete(Long projectId) {

        return 1 == projectMapper.delete(projectId);

    }
}
