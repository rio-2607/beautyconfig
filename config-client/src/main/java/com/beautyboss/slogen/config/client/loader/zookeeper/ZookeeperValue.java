package com.beautyboss.slogen.config.client.loader.zookeeper;

import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Data
public class ZookeeperValue {

    public static final ZookeeperValue NULL_VALUE = new ZookeeperValue();

    private String value;

    private long timestamp;

    public boolean isNullValue() {
        return this.equals(NULL_VALUE);
    }

    public String toString() {
        return String.format("ZookeeperValue[value=%s, timestamp=%s]", value, timestamp);
    }

}
