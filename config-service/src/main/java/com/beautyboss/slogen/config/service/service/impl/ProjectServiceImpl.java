package com.beautyboss.slogen.config.service.service.impl;

import com.beautyboss.slogen.config.common.utils.LocalCache;
import com.beautyboss.slogen.config.common.utils.LocalCacheFactory;
import com.beautyboss.slogen.config.service.domain.Project;
import com.beautyboss.slogen.config.service.repository.ProjectRepository;
import com.beautyboss.slogen.config.service.service.ProjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectRepository projectRepository;

    private LocalCache<Long,Project> cache = LocalCacheFactory.createCache(null,null);


    @Override
    public Long create(Project project) {
        return projectRepository.create(project);
    }

    @Override
    public Project query(Long projectId) {
        Project project = cache.get(projectId);
        if(null != project) {
            return project;
        }
        project = projectRepository.query(projectId);
        if(null != project) {
            cache.put(projectId,project);
        }
        return project;
    }

    @Override
    public boolean modify(Project project) {
        return projectRepository.modify(project);
    }

    @Override
    public boolean delete(Long projectId) {
        return projectRepository.delete(projectId);
    }

}
