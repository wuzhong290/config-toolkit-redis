${rootNode}&${version}&${key}构成了key名称

每一个RedisConfigGroup消息，都有一组key=channelTopic.
1、初始化获取 利用strings数据类型及操作,通过key获取信息
    ValueOperations<Object, Object> ops =redisTemplate.opsForValue();
    Object value = ops.get(getRootNodeVersionKey());
2、实时跟新利用 redis Pub/Sub"发布/订阅" 进行  通过监听channelTopic实时进行跟新


3、key的序列化采用StringRedisSerializer
   value的序列化采用Jackson2JsonRedisSerializer
   channelTopic的监听序列化也是采用Jackson2JsonRedisSerializer
4、所以往redis上发送配置信息时要配置好序列化类




