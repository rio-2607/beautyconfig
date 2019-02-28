package com.beautyboss.slogen.config.resource.transfer;

import com.beautyboss.slogen.config.resource.entity.ProjectEntity;
import com.beautyboss.slogen.config.service.domain.Project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public class ProjectTransfer extends Transfer {

    public static Project transfer2DO(ProjectEntity entity) {
        Project project = Transfer.transfer(entity,Project.class);
        if(null == project) {
            return null;
        }
        List<String> admins = Arrays.stream(entity.getAdmins().split(",")).collect(Collectors.toList());
        project.setAdmins(admins);
        return project;
    }

    public static ProjectEntity transfer2Entity(Project project) {
        ProjectEntity entity = transfer2Entity(project,ProjectEntity.class);
        String admins = project.getAdmins().stream().collect(Collectors.joining(","));
        entity.setAdmins(admins);
        return entity;
    }
}
