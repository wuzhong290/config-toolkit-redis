${rootNode}/${version}/${key}������key����

ÿһ��RedisConfigGroup��Ϣ������һ��key=channelTopic.
1����ʼ����ȡ ����strings�������ͼ�����,ͨ��key��ȡ��Ϣ
    ValueOperations<Object, Object> ops =redisTemplate.opsForValue();
    Object value = ops.get(getRootNodeVersionKey());
2��ʵʱ�������� redis Pub/Sub"����/����" ����  ͨ������channelTopicʵʱ���и���





