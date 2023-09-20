package com.example.doodle.Redis;

import com.example.doodle.Pen.Pen;
import com.example.doodle.Pen.PenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisSub redisSub;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final PenRepository penRepository;
    private Map<String, ChannelTopic> topicMap;
    @PostConstruct
    private void init() {
        topicMap = new HashMap<>();
        Iterable<Pen> penList = penRepository.findAll();
        for(Pen draw : penList) {
            String drawId = draw.getId();
            ChannelTopic topic = ChannelTopic.of(drawId);
            redisMessageListenerContainer.addMessageListener(redisSub, topic);
            topicMap.put(drawId,topic);
        }

    }
    public void subscribe(String canvasId) {
        ChannelTopic topic = topicMap.get(canvasId);
        if(topic == null) {
            topic = ChannelTopic.of(canvasId);
            redisMessageListenerContainer.addMessageListener(redisSub, topic);
            topicMap.put(canvasId, topic);
        }
    }

    public ChannelTopic getTopic(String canvasId) {
        return topicMap.get(canvasId);
    }

}
