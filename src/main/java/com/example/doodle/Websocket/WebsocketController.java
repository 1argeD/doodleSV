package com.example.doodle.Websocket;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Canvas.CanvasService;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Member.Member;
import com.example.doodle.Redis.RedisPub;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private CanvasService canvasService;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisPub redisPub;
    private final CanvasRepository canvasRepository;

    @MessageMapping(value = "pub/canvas/{canvasId}")
    public void sendSpot(@DestinationVariable String canvasId, Member user) {

    }

}
