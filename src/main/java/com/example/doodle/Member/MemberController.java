package com.example.doodle.Member;

import com.example.doodle.Exception.BadRequestException;
import com.example.doodle.Login.Dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/member/sign")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) throws BadRequestException {
        memberService.signup(requestDto);
        return ResponseEntity.ok().body(Map.of("msg", "회원가입이 완료 되었습니다."));
    }

    @PostMapping(value ="/member/eCheck")
    public ResponseEntity<?> emailCheck(@RequestBody String email) {
        memberService.checkEmailIsDuplication(email);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 이메일입니다."));
    }

    @PostMapping(value ="/member/nickCheck")
    public ResponseEntity<?> nickCheck(@RequestBody String nickname) {
        memberService.checkNicknameDuplication(nickname);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 닉네임입니다."));
    }
}
