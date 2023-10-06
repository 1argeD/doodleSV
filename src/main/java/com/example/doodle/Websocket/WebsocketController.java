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
import org.bson.json.JsonObject;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

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


    @MessageMapping(value = "/testSub/{canvasId}")
    public void enter(@DestinationVariable String canvasId,String test) {
        log.info("연결이 됐으면 이 로그를 띄움" + canvasId);
        log.info("test내용 확인 : "+test);
        simpMessagingTemplate.convertAndSend("/sub/testSub/"+canvasId, test);
    }

}
