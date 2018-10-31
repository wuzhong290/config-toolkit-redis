package com.dangdang.config.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisPub {

    public static void main(String[] args) {
        RedisConfigProfile  configProfile = new RedisConfigProfile("1.0");
        configProfile.setRootNode("mob-java-mop");
        configProfile.setHost("192.168.11.248");
        configProfile.setPort(6379);
        configProfile.setPassword("NJqg_redis@248");

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(configProfile.getHost());
        factory.setPort(configProfile.getPort());
        factory.setPassword(configProfile.getPassword());
        factory.afterPropertiesSet();

        RedisTemplate redisTemplate = new RedisTemplate();
        RedisSerializer keySerializer = new StringRedisSerializer();
        RedisSerializer valueRedisSerializer = new Jackson2JsonRedisSerializer(RedisConfig.class);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();

        RedisConfig config = new RedisConfig();
        List<RedisItemConfig> itemConfigs = new ArrayList<>();
        RedisItemConfig redisItemConfig = new RedisItemConfig();
        Map<String, String> configs = new HashMap<>();
        configs.put("name","wuzhong11111");
        redisItemConfig.setConfigs(configs);
        itemConfigs.add(redisItemConfig);
        config.setItemConfigs(itemConfigs);
        System.out.println(JSON.toJSONString(config));
        redisTemplate.convertAndSend(configProfile.getRootNode() +"&" + configProfile.getVersion()  +"&test",config);
    }
}
