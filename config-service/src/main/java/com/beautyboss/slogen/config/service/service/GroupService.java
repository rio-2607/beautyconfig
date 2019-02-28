package com.beautyboss.slogen.config.service.service;

import com.beautyboss.slogen.config.service.domain.Group;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public interface GroupService {

    Long create(Group group);

    Group query(Long groupId);

    boolean update(Group group);

    boolean delete(Long groupId);

    List<Group> queryAll(Long projectId);

}
