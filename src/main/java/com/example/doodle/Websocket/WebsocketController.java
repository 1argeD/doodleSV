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
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/test")
    public String plz() {
        return "제발 돼라 도ㅓㅐ라 제바ㅣㅣㅣㅣㅣㄹ";
    }

    @MessageMapping(value = "/testSub/{canvasId}")
    @SendTo("/sub/testSub/{canvasId}")
    public String enter(@DestinationVariable String canvasId) {
        log.info("연결이 됐으면 이 로그를 띄움"+canvasId);
     return "연결이 되었으니 서버에서 보냄";
    }

    @MessageMapping(value = "/testPub")
    public void test(@DestinationVariable String test) {
        log.info(test);
        String canvasId = test.split(",")[0];
        redisPub.testPublish(new ChannelTopic(canvasId), test);
    }

}
