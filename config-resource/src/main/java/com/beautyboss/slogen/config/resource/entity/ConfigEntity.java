package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class ConfigEntity extends BaseEntity {

    private String key;

    private String desc;

    private Integer type;

    private Long projectId;

    private Long groupId;

    private boolean encryped;

    private Integer seq;

}
