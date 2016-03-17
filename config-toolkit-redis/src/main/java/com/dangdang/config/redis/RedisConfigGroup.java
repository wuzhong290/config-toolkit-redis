package com.dangdang.config.redis;

import com.dangdang.config.service.ConfigGroup;
import com.dangdang.config.service.GeneralConfigGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wuzhong on 2016/3/16.
 */
public class RedisConfigGroup<T> extends GeneralConfigGroup {

    private static final long serialVersionUID = 1L;

    private RedisConfigProfile configProfile;

    private String key;

    private RedisTemplate redisTemplate;

    private RedisMessageListenerContainer container;

    private MessageListener listener = new ChannelMessageListener(this);

    public RedisConfigGroup(RedisConfigProfile configProfile, String key) {
        this(null, configProfile, key);
    }

    public RedisConfigGroup(ConfigGroup internalConfigGroup, RedisConfigProfile configProfile, String key) {
        super(internalConfigGroup);
        this.configProfile = configProfile;
        this.key = key;
        initConfigs();
    }

    /**
     * 初始化节点
     */
    private void initConfigs() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(configProfile.getHost());
        factory.setPort(configProfile.getPort());
        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);

        container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listener, new ChannelTopic(key));

        loadNode();
    }

    //加载节点信息
    void loadNode() {
        if(redisTemplate.hasKey(key)){
            DataType dt = redisTemplate.type(key);
            if (StringUtils.equals(dt.code(), "string")) {
                ValueOperations<Object, T> ops =redisTemplate.opsForValue();
                T value = ops.get(key);
                //TODO 通过value进行super.put();super.putAll();
            } else if (StringUtils.equals(dt.code(), "list")) {
                ListOperations<Object, T> ops =redisTemplate.opsForList();
                List<T> value = ops.range(key, 0, -1);
                //TODO 通过value进行super.put();super.putAll();
            } else if (StringUtils.equals(dt.code(), "set")) {
                SetOperations<Object, T> ops =redisTemplate.opsForSet();
                Set<T> value = ops.members(key);
                //TODO 通过value进行super.put();super.putAll();
            } else if (StringUtils.equals(dt.code(), "zset")) {
                ZSetOperations<Object, T> ops =redisTemplate.opsForZSet();
                Set<T> value = ops.range(key, 0, -1);
                //TODO 通过value进行super.put();super.putAll();
            } else if (StringUtils.equals(dt.code(), "hash")) {
                HashOperations<Object, Object, Object> ops =redisTemplate.opsForHash();
                Map value = ops.entries(key);
                //TODO 通过value进行super.put();super.putAll();
            }
        }
    }

    //加载节点信息
    void loadNode(Message message, byte[] bytes) {
        if (message == null) {
            //不做任何处理
        } else if (message.getClass().isArray()) {
            //TODO 通过message进行super.put();super.putAll();
        } else if (message instanceof List<?>) {
            //TODO 通过message进行super.put();super.putAll();
        } else if (message instanceof Map<?, ?>) {
            //TODO 通过message进行super.put();super.putAll();
        } else {
            //
        }
    }

    @Override
    public void close() throws IOException {
        if(container != null){
            try {
                container.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
