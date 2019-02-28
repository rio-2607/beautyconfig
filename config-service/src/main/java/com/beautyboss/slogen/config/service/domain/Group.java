package com.beautyboss.slogen.config.service.domain;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
@Data
public class Group extends BaseDO {


    /**
     * 组名称
     */
    private String name;

    /**
     * 组描述
     */
    private String desc;

    /**
     * 所属项目
     */
    private Long projectId;

    public static Group create(String name,Long projectId,String desc) {
        Group group = new Group();
        group.setName(name);
        group.setProjectId(projectId);
        group.setDesc(desc);
        return group;
    }


}
