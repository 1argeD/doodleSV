import com.example.doodle.Login.UserDetailsImpl;
import com.example.doodle.Websocket.WebsocketController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final WebsocketController websocketController;
    @GetMapping(value = "/test1/{canvasId}")
    ResponseEntity<?> testEnter(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String canvasId, @RequestBody String test) {
      log.info("연결이 되면 이걸 보여줌");
      return ResponseEntity.ok().body(Map.of("MSG", "연결이 되었습니다 - 서버에서 보냄"));
    }
}
