package com.beautyboss.slogen.config.client;

import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
@Data
public class ConfigEvent {

    private String key;

    private ConfigEventType type;

    private ConfigValue value;

    private ConfigValue oldValue;

    private String toStr;

    public ConfigEvent(String key,ConfigValue value,ConfigValue oldValue) {
        this.key = key;
        this.value = value;
        this.oldValue = oldValue;
        this.type = ConfigEventType.CONFIG_CHANGE;
    }

    public ConfigEvent(String key,ConfigEventType type,ConfigValue value,ConfigValue oldValue) {
        this.key = key;
        this.value = value;
        this.oldValue = oldValue;
        this.type = type;
    }

    public String getValue() {
        return value == null ? null : value.getValue();
    }

    public String getOldValue() {
        return oldValue == null ? null : oldValue.getValue();
    }

    public String toString() {
        if (toStr == null) {
            StringBuilder buf = new StringBuilder(64);
            buf.append(getClass().getSimpleName()).append('[').append("key=").append(key)
                    .append(", type=").append(type)
                    .append(", value=").append(getValue())
                    .append(", oldValue=").append(getOldValue()).append(']');
            toStr = buf.toString();
        }
        return toStr;
    }

    public ConfigValue getConfigValue() {
        return value;
    }

    public void setOldConfigValue(ConfigValue oldValue) {
        this.oldValue = oldValue;
    }
}
