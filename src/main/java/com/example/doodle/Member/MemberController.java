package com.example.doodle.Member;

import com.example.doodle.Login.Dto.SignupRequestDto;
import com.example.doodle.Member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Temporal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private MemberService memberService;

    @PostMapping("/member/sign")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto){
        memberService.signup(requestDto);
        return ResponseEntity.ok().body(Map.of("msg", "회원가입이 완료 되었습니다."));
    }

    @PostMapping("/member/eCheck")
    public ResponseEntity<?> emailCheck(@RequestBody String email) {
        memberService.checkEmailIsDuplication(email);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 이메일입니다."));
    }

    @PostMapping("/member/nickCheck")
    public ResponseEntity<?> nickCheck(@RequestBody String nickname) {
        memberService.checkNicknameDuplication(nickname);
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 닉네임입니다."));
    }


}
