package com.beautyboss.slogen.config.service.repository;

import com.beautyboss.slogen.config.service.domain.Group;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface GroupRepository {

    Long add(Group group);

    Group query(Long groupId);

    List<Group> queryAll(Long projectId);

    boolean update(Group group);

    boolean delete(Long groupId);

}
