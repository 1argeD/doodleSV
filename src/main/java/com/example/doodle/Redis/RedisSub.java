package com.example.doodle.Redis;


import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Pen.DTO.PenResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
@Slf4j
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
//            String penInfo = objectMapper.readValue(publishMessage, String.class);
//            String[] penInfoSlice = penInfo.split(":");
//            String canvasId = penInfoSlice[1] .split(",")[0]
//                    .replace("\"","");
//            log.info("캔버스 아이디 값 올바르게 찾았는지 확인해 보기 : "+canvasId);
            //태스트를 위해서 값 일시 변경
            PenResponseDTO pen = objectMapper.readValue(publishMessage, PenResponseDTO.class);
            Canvas canvasId = canvasRepository.findCanvasById(pen.getCanvas_id());
            simpMessageSendingOperations.convertAndSend("/sub/canvas/"+canvasId, message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
