package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class ConfigInstanceEntity extends BaseEntity {

    private long configId;

    private int envId;

    private String value;

}
