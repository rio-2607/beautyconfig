package com.beautyboss.slogen.config.service.enums;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
public enum RegisterTypeEnum {

    Zookeeper(101,"zookeeper"),
    Redis(102,"redis");

    private int type;

    private String desc;

    RegisterTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
