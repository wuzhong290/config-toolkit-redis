${rootNode}&${version}&${key}������key����

ÿһ��RedisConfigGroup��Ϣ������һ��key=channelTopic.
1����ʼ����ȡ ����strings�������ͼ�����,ͨ��key��ȡ��Ϣ
    ValueOperations<Object, Object> ops =redisTemplate.opsForValue();
    Object value = ops.get(getRootNodeVersionKey());
2��ʵʱ�������� redis Pub/Sub"����/����" ����  ͨ������channelTopicʵʱ���и���


3��key�����л�����StringRedisSerializer
   value�����л�����Jackson2JsonRedisSerializer
   channelTopic�ļ������л�Ҳ�ǲ���Jackson2JsonRedisSerializer
4��������redis�Ϸ���������ϢʱҪ���ú����л���




