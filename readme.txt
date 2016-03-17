${rootNode}/${version}/${key}构成了key名称

每一个RedisConfigGroup消息，都有一组key=channelTopic.
1、初始化获取 利用strings数据类型及操作,通过key获取信息
    ValueOperations<Object, Object> ops =redisTemplate.opsForValue();
    Object value = ops.get(getRootNodeVersionKey());
2、实时跟新利用 redis Pub/Sub"发布/订阅" 进行  通过监听channelTopic实时进行跟新





