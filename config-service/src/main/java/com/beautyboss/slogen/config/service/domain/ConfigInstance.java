package com.beautyboss.slogen.config.service.domain;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/10
 */
@Data
public class ConfigInstance extends BaseDO {

    private long configId;

    private int envId;

    private String value;

    public static class Builder {

        private ConfigInstance instance;

        public Builder() {
            instance = new ConfigInstance();
        }

        public Builder envId(Integer envId) {
            instance.envId = envId;
            return this;
        }

        public Builder configId(Long configId) {
            instance.configId = configId;
            return this;
        }

        public Builder value(String value) {
            instance.value = value;
            return this;
        }

        public ConfigInstance build() {
            return instance;
        }
    }

}
