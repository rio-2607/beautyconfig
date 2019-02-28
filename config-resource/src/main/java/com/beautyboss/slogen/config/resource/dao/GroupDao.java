package com.beautyboss.slogen.config.resource.dao;

import com.beautyboss.slogen.config.resource.entity.GroupEntity;
import com.beautyboss.slogen.config.resource.mapper.GroupMapper;
import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Group;
import com.beautyboss.slogen.config.service.repository.GroupRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/25
 */
@Repository
public class GroupDao implements GroupRepository{

    @Resource
    private GroupMapper groupMapper;

    @Override
    public Long add(Group group) {
        GroupEntity entity = Transfer.transfer(group,GroupEntity.class);
        groupMapper.add(entity);
        return entity.getId();
    }

    @Override
    public Group query(Long groupId) {
        GroupEntity entity = groupMapper.query(groupId);
        return Transfer.transfer(entity,Group.class);
    }

    @Override
    public List<Group> queryAll(Long projectId) {
        List<GroupEntity> entities = groupMapper.queryByProject(projectId);
        return entities.stream().map(e -> Transfer.transfer(e,Group.class)).collect(Collectors.toList());
    }

    @Override
    public boolean update(Group group) {
        GroupEntity entity = Transfer.transfer2Entity(group,GroupEntity.class);
        return 1 == groupMapper.update(entity);
    }

    @Override
    public boolean delete(Long groupId) {
        return 1 == groupMapper.delete(groupId);
    }
}
