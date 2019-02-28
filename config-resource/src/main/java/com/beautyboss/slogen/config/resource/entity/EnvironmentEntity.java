package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class EnvironmentEntity extends BaseEntity {


    private String name;

    /**
     * 开发环境/测试环境/预发环境/小流量环境/线上环境
     */
    private int type;

    private int registerType;

    private String ips;

}
