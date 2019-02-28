package com.beautyboss.slogen.config.client;

import com.beautyboss.slogen.config.client.manager.ConfigContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Data
public class ConfigValue {

    private static final String SOURCE_LAST_WORKING = "LAST_WORKING";

    private String key;

    private String value;

    private ConfigContext context;

    private Long modifyTime;

    // 数据由那个ConfigLoader加载的
    private String source;

    public ConfigValue() {
    }

    @JsonIgnore
    public boolean isLastWorkingConfig() {
        return SOURCE_LAST_WORKING.equals(source);
    }

    public ConfigValue(String key, String value, String source) {
        this.setKey(key);
        this.value = value;
        this.source = source;
    }


}
