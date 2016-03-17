package com.dangdang.config.redis;

import com.dangdang.config.service.ConfigGroup;
import com.dangdang.config.service.GeneralConfigGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by wuzhong on 2016/3/16.
 */
public class RedisConfigGroup extends GeneralConfigGroup {

    private static final long serialVersionUID = -3826360597354359856L;

    private RedisConfigProfile configProfile;

    private String key;

    private Thread shutdownHook;

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
        factory.afterPropertiesSet();

        redisTemplate = new RedisTemplate();
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer valueRedisSerializer = new Jackson2JsonRedisSerializer(RedisConfig.class);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueRedisSerializer);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();

        container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listener, new ChannelTopic(getRootNodeVersionKey()));
        container.afterPropertiesSet();
        container.start();

        loadNode();

        registerShutdownHook();
    }

    //加载节点信息
    void loadNode() {
        if(redisTemplate.hasKey(getRootNodeVersionKey())){
            DataType dt = redisTemplate.type(getRootNodeVersionKey());
            if (StringUtils.equals(dt.code(), "string")) {
                ValueOperations<Object, Object> ops =redisTemplate.opsForValue();
                RedisConfig value = (RedisConfig)ops.get(getRootNodeVersionKey());
                redisConfigUpdate(value);
            }
        }
    }

    //加载节点信息
    void loadNode(Object boy, String channelTopic) {
        if (boy == null) {
            //不做任何处理
        } else if (boy instanceof RedisConfig) {
            redisConfigUpdate((RedisConfig)boy);
        }
    }

    void redisConfigUpdate(RedisConfig value){
        if(null != value && null != value.getItemConfigs() && value.getItemConfigs().size()>0){
            for (RedisItemConfig item : value.getItemConfigs()){
                super.putAll(item.getConfigs());
            }
        }
    }

    public String getRootNodeVersionKey() {
        return  configProfile.getRootNode() +"&" + configProfile.getVersion()  +"&" + key;
    }

    public void registerShutdownHook() {
        if(this.shutdownHook == null) {
            this.shutdownHook = new Thread() {
                public void run() {
                    RedisConfigGroup.this.close();
                }
            };
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        }

    }

    public void close() {
        if(container != null){
            try {
                container.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
