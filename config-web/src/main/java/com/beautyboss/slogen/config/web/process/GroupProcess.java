package com.beautyboss.slogen.config.web.process;

import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Group;
import com.beautyboss.slogen.config.service.service.GroupService;
import com.beautyboss.slogen.config.web.dto.GroupDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
public class GroupProcess {

    @Resource
    private GroupService groupService;

    public Long create(GroupDTO request) {
        Group group = Transfer.transfer(request,Group.class);
        return groupService.create(group);
    }

    public boolean delete(Long groupId) {
        return groupService.delete(groupId);
    }

    public GroupDTO query(Long groupId) {
        Group group = groupService.query(groupId);
        return Transfer.transfer(group,GroupDTO.class);
    }

    public List<GroupDTO> queryAll(Long projectId) {
        List<Group> groups = groupService.queryAll(projectId);
        return groups.stream().map(g -> Transfer.transfer(g,GroupDTO.class)).collect(Collectors.toList());
    }

    public boolean update(GroupDTO request) {
        Group group = Transfer.transfer(request,Group.class);
        return groupService.update(group);
    }

}
