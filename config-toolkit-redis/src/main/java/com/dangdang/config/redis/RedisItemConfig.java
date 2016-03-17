package com.dangdang.config.redis;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by wuzhong on 2016/3/17.
 */
public class RedisItemConfig implements Serializable {

    private static final long serialVersionUID = 3237435926437337908L;

    private Map<String,String> configs;

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }
}
