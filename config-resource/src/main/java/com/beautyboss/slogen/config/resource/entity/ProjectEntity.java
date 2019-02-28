package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;


/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class ProjectEntity extends BaseEntity {

    private String name;

    private String desc;

    private String admins;

}
