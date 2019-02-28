package com.beautyboss.slogen.config.service.service.impl;

import com.beautyboss.slogen.config.service.domain.Group;
import com.beautyboss.slogen.config.service.repository.GroupRepository;
import com.beautyboss.slogen.config.service.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupRepository groupRepository;

    @Override
    public Long create(Group group) {
        return groupRepository.add(group);
    }

    @Override
    public Group query(Long groupId) {
        return groupRepository.query(groupId);
    }

    @Override
    public boolean update(Group group) {
        return groupRepository.update(group);
    }

    @Override
    public boolean delete(Long groupId) {
        return groupRepository.delete(groupId);
    }

    @Override
    public List<Group> queryAll(Long projectId) {
        return groupRepository.queryAll(projectId);
    }

}
