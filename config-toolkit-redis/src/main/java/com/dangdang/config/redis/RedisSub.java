package com.dangdang.config.redis;

/**
 * Created by wuzhong on 2016/3/17.
 */
public class RedisSub {

    public static void main(String[] args) {
        RedisConfigProfile  configProfile = new RedisConfigProfile("1.0");
        configProfile.setRootNode("mob-java-mop");
        configProfile.setHost("192.168.11.248");
        configProfile.setPort(6379);
        configProfile.setPassword("NJqg_redis@248");

        RedisConfigGroup redisConfigGroup = new RedisConfigGroup(configProfile,"test");

    }
}
