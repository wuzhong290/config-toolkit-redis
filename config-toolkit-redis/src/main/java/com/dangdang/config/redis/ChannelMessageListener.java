package com.dangdang.config.redis;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by wuzhong on 2016/3/16.
 */
public class ChannelMessageListener implements MessageListener {

    private Logger logger = Logger.getLogger(ChannelMessageListener.class);

    private final RedisConfigGroup configGroup;

    StringRedisSerializer serializer = new StringRedisSerializer();
    Jackson2JsonRedisSerializer jacksonJsonRedisSerializer = new Jackson2JsonRedisSerializer(RedisConfig.class);

    public ChannelMessageListener(RedisConfigGroup configGroup) {
        this.configGroup = Preconditions.checkNotNull(configGroup);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String channelTopic = serializer.deserialize(message.getChannel());
        Object body = jacksonJsonRedisSerializer.deserialize(message.getBody());

        configGroup.loadNode(body,channelTopic);
    }
}
