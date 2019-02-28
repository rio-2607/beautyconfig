package com.beautyboss.slogen.config.service.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class Environment extends BaseDO {

    private String name;

    /**
     * 开发环境/测试环境/预发环境/小流量环境/线上环境
     */
    private int type;

    /**
     * 注册类型 @See RegistryTypeEnum
     */
    private int registerType;

    private List<String> ips;

}
