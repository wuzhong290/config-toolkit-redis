package com.dangdang.config.redis;

import com.google.common.base.Preconditions;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Created by wuzhong on 2016/3/16.
 */
public class ChannelMessageListener implements MessageListener {

    private final RedisConfigGroup configGroup;


    public ChannelMessageListener(RedisConfigGroup configGroup) {
        this.configGroup = Preconditions.checkNotNull(configGroup);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        //TODO 用message重新设置configGroup
        configGroup.loadNode(message,bytes);
    }
}
