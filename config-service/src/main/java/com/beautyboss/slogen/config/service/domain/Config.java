package com.beautyboss.slogen.config.service.domain;

import com.beautyboss.slogen.config.service.enums.ConfigTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
@Data
public class Config extends BaseDO {


    /**
     * 配置key
     */
    private String key;

    /**
     * 配置描述
     */
    private String desc;

    /**
     * value类型 @See ConfigTypeEnum
     */
    private Integer type;

    private ConfigTypeEnum typeEnum;

    /**
     * 所属项目
     */
    private Long projectId;

    private Long groupId;

    /**
     * 是否加密
     */
    private boolean encryped;

    private Integer seq;


    public static class Builder {

        private Config config;

        public Builder() {
            config = new Config();
        }

        public Builder key(String key) {
            config.key = key;
            return this;
        }

        public Builder desc(String desc) {
            config.desc = desc;
            return this;
        }

        public Builder type(Integer type) {
            config.type = type;
            return this;
        }

        public Builder projectId(Long projectId) {
            config.projectId = projectId;
            return this;
        }

        public Builder groupId(Long groupId) {
            config.groupId = groupId;
            return this;
        }

        public Config build() {
            return config;
        }

    }




}
