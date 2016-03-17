package com.dangdang.config.redis;

/**
 * Created by wuzhong on 2016/3/17.
 */
public class MainEntrance {

    public static void main(String[] args) {
        RedisConfigProfile  configProfile = new RedisConfigProfile("1.0");
        configProfile.setRootNode("mob-java-mop");
        configProfile.setHost("10.10.30.200");
        configProfile.setPort(6377);

        RedisConfigGroup redisConfigGroup = new RedisConfigGroup(configProfile,"test");

    }
}
