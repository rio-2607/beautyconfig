package com.beautyboss.slogen.config.web.process;

import com.beautyboss.slogen.config.common.Consts;
import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Group;
import com.beautyboss.slogen.config.service.domain.Project;
import com.beautyboss.slogen.config.service.service.GroupService;
import com.beautyboss.slogen.config.service.service.ProjectService;
import com.beautyboss.slogen.config.web.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
public class ProjectProcess {

    @Resource
    private ProjectService projectService;

    @Resource
    private GroupService groupService;

    public Long save(ProjectDTO request) {
        Project project = Transfer.transfer(request,Project.class);
        Long projectId = projectService.create(project);
        // 每个新建的project都默认添加一个名字叫做default的group
        Group group = Group.create(Consts.DEFAULT_GROUP,projectId, Consts.DEFAULT_GROUP);
        groupService.create(group);
        return projectId;

    }

    public ProjectDTO query(Long id) {
        Project project = projectService.query(id);
        return Transfer.transfer(project,ProjectDTO.class);
    }
    public boolean delete(Long id) {
        return projectService.delete(id);
    }

    public boolean update(ProjectDTO request) {
        Project project = Transfer.transfer(request,Project.class);
        return projectService.modify(project);
    }


}
