package com.example.doodle.Redis;


import com.example.doodle.Pen.DTO.PenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPub {
    private final RedisTemplate<String, Object> redisTemplate;
    /*그림 그리기*/
    public void drawPublish(ChannelTopic channelTopic, PenResponseDTO pen) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), pen);
    }
    
    /*메세지 보내기
    * public void chatPublish(ChannelTopic channelTopic, ChatDto message) {
    *  redisTemplate.convertAndSend(channelTopic.getTopic(), message)}
    * */
}
