package com.dangdang.config.redis;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuzhong on 2016/3/17.
 */
public class RedisConfig implements Serializable{

    private static final long serialVersionUID = 3609292769083919767L;

    private List<RedisItemConfig> itemConfigs;

    public List<RedisItemConfig> getItemConfigs() {
        return itemConfigs;
    }

    public void setItemConfigs(List<RedisItemConfig> itemConfigs) {
        this.itemConfigs = itemConfigs;
    }
}
