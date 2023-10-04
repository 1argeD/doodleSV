package com.example.doodle.Websocket;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Canvas.CanvasService;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Member.Member;
import com.example.doodle.Redis.RedisPub;
import com.example.doodle.Redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private CanvasService canvasService;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisPub redisPub;
    private final CanvasRepository canvasRepository;
    private final RedisRepository redisRepository;

    @MessageMapping(value = "/canvas/{canvasId}")
    public void sendSpot(@DestinationVariable String canvasId, Member user) {

    }
    @MessageMapping(value = "/test1/{canvasId}")
    public void enter(@DestinationVariable String memberId, String canvasId) {
        CanvasResponseDto canvas = canvasService.getOneCanvas(memberId, canvasId);
        log.info("연결됐으면 이게 보일거임, 추가로 캔버스 내용도 좀 확인하고 : " + canvas);
        redisRepository.subscribe(canvasId);

    }

}
