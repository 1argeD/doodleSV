package com.example.doodle.Canvas;

import com.example.doodle.Canvas.Dto.CanvasRequestDto;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Login.UserDetailsImpl;
import com.example.doodle.Member.Member;
import com.example.doodle.Member.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class CanvasController {
    private final CanvasService canvasService;
    private final MemberRepository memberRepository;
    private final CanvasRepository canvasRepository;

    @PostMapping("/canvas")
    public ResponseEntity<?> makeCanvas(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CanvasRequestDto canvasRequestDto) {

        Optional<Member> member = memberRepository.findById(userDetails.getMember().getId());
        CanvasResponseDto canvasResponseDto = canvasService.makeCanvas(member, canvasRequestDto);

        return ResponseEntity.ok().body(canvasResponseDto);
    }

    @GetMapping("/canvas/get")
    public ResponseEntity<?> getCanvas(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Optional<Member> member = memberRepository.findById(userDetails.getMember().getId());

        List<CanvasResponseDto> canvasList = canvasService.getCanvas(member);

        return ResponseEntity.ok().body(canvasList);
    }

    @GetMapping("/canvas/get/{canvasId}")
    public ResponseEntity<?> getOneCanvas(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable String canvasId) {
        Member user = userDetails.getMember();
        CanvasResponseDto canvas = canvasService.getOneCanvas(user, canvasId);
        return ResponseEntity.ok().body(canvas);

    }

    @PutMapping("/canvas/update")
    public ResponseEntity<?> updateCanvas(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CanvasRequestDto requestDto) {

        Member member = userDetails.getMember();
        CanvasResponseDto canvasResponseDto = canvasService.updateCanvas(member, requestDto);

        return ResponseEntity.ok().body(Map.of("canvas_id",canvasResponseDto.getId(), "canvas_title", canvasResponseDto.getCanvasTitle()));
    }

    @DeleteMapping("/canvas/delete/{canvasId}")
    public ResponseEntity<?> deleteCanvas(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String canvasId) {

        Optional<Member> member = memberRepository.findById(userDetails.getMember().getId());
        if(member.isEmpty()) {
            throw new IllegalArgumentException("회원정보가 없습니다.");
        }
        canvasService.deleteCanvas(member.get().getId(), canvasId);

        return ResponseEntity.ok().body(Map.of("msg", "캔버스를 삭제하였습니다."));
    }

    @PutMapping("/canvas/invite/{canvasId}")
    public ResponseEntity<?>canvasInvite(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody CanvasRequestDto requestDto, @PathVariable String canvasId) throws InterruptedException {

        Optional<Member> member = memberRepository.findById(userDetails.getMember().getId());
        CanvasResponseDto canvasResponseDto = canvasService.invite(member, canvasId, requestDto);

        return ResponseEntity.ok().body(Map.of("with", canvasResponseDto.getWith(),"msg", "초대 성공"));
    }
}
