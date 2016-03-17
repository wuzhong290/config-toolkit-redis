package com.dangdang.config.redis;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wuzhong on 2016/3/17.
 */
public class RedisItemConfig implements Serializable {

    private static final long serialVersionUID = 3237435926437337908L;

    private String suffix;
    private String prefix;

    private Map<String,Object> configs;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Map<String, Object> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, Object> configs) {
        this.configs = configs;
    }
}
