package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class GroupEntity extends BaseEntity {

    private String name;

    private String desc;

    private Long projectId;

}
