package com.example.doodle.Websocket;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Canvas.CanvasService;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Login.UserDetailsImpl;
import com.example.doodle.Member.Member;
import com.example.doodle.Pen.Pen;
import com.example.doodle.Pen.PenService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final PenService penService;

    @MessageMapping(value = "/canvas/{canvasId}")
    public void enter(@DestinationVariable String canvasId, String penInfo) {
        String memberId = penInfo.split(":")[1]
                .split(",")[0].replace("\"","");
        String penColor = penInfo.split(":")[2]
                .split(",")[0].replace("\"","");
        String spot = penInfo.split(":")[3]
                .replace("}","")
                .replace("[","")
                .replace("]","").replace("\"","");

        log.info("팬 정보 확인 : "+penInfo);
        log.info("아이디 확인 : "+ memberId);
        log.info("컬러 정보 확인 : "+ penColor);
        log.info("점 정보 확인 : "+ spot);
        log.info("캔버스 아이디 : " + canvasId);

//spot을 ArrayList 데이터로 통신을 해야할지 그냥 String값으로 저장해두고 사용해야 할 지 아직 잘 모르겠음. 통신은 String이 용이한데, 수정은 배열값이 더 편할 거 같음. 그렇다고 계속 바꿔주기엔 쓸데없이 연산이 추가로 늘어날 가능성도 있음
        penService.penCreate(memberId,penColor,spot,canvasId);
    }

}
