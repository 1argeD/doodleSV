package com.example.doodle.Pen;

import com.example.doodle.Login.RefreshToken.RefreshTokenRepository;
import com.example.doodle.Login.UserDetailsImpl;
import com.example.doodle.Member.Member;
import com.example.doodle.Pen.DTO.PenRequestDTO;
import com.example.doodle.Pen.DTO.PenResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
public class PenController {
    private final PenService penService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping(value = "/canvas/pen/{canvasId}")
    public ResponseEntity<?> createPen (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody PenRequestDTO penRequestDTO, @PathVariable String canvasId) {
        Member member = userDetails.getMember();
        PenResponseDTO penResponseDTO = penService.create(member, penRequestDTO ,canvasId);
        return ResponseEntity.ok().body(penResponseDTO);
    }

    @PutMapping(value = "/canvas/pen-put")
    public ResponseEntity<?> penPut(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody PenRequestDTO penRequestDTO) {
        Member member = userDetails.getMember();
        String member_id = member.getId();
        Pen pen = penService.search(member_id);
        List<String> updateSpot = penService.penUpdate(pen, penRequestDTO.getSpot());
        return ResponseEntity.ok().body(Map.of("spot", updateSpot));
    }

    @DeleteMapping("/canvas/pen-delete")
    public ResponseEntity<?> penDelete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        penService.penDelete(member.getId(), member.getNickname());
        return ResponseEntity.ok().body(Map.of("msg", "도형 삭제"));
    }
}
