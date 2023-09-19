package com.example.doodle.Redis;


import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Pen.DTO.PenResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@RequiredArgsConstructor
@Service
public class RedisSub implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final CanvasRepository canvasRepository;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            PenResponseDTO pen = objectMapper.readValue(publishMessage, PenResponseDTO.class);
            Canvas canvas = canvasRepository.findCanvasById(pen.getCanvas_id());
            simpMessageSendingOperations.convertAndSend("/sub/canvas/"+canvas.getId(), message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
