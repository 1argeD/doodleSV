package com.example.doodle.Redis;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
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
    private final CanvasRepository canvasRepository;

    @PostConstruct
    private void init() {
        topicMap = new HashMap<>();
        Iterable<Canvas> canvasList = canvasRepository.findAll();
        for(Canvas canvas : canvasList) {
            String canvasId = canvas.getId();
            ChannelTopic topic = ChannelTopic.of(canvasId);
            redisMessageListenerContainer.addMessageListener(redisSub, topic);
            topicMap.put(canvasId,topic);
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
