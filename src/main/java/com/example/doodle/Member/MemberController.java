package com.example.doodle.Member;

import com.example.doodle.Exception.BadRequestException;
import com.example.doodle.Login.Dto.LoginRequestDto;
import com.example.doodle.Login.Dto.SignupRequestDto;
import com.example.doodle.Login.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/member/sign-up")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) throws BadRequestException {
        memberService.signup(requestDto);
        return ResponseEntity.ok().body(Map.of("msg", "회원가입이 완료 되었습니다."));
    }

    @PostMapping(value = "/member/e-check")
    public ResponseEntity<?> emailCheck(@RequestBody String email) {
        if (!memberService.checkEmailIsDuplication(email)) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 이메일입니다."));
    }

    @PostMapping(value = "/member/nick-check")
    public ResponseEntity<?> nickCheck(@RequestBody String nickname) {
        if (!memberService.checkNicknameDuplication(nickname)) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        return ResponseEntity.ok().body(Map.of("msg", "사용 가능한 닉네임입니다."));
    }

    @PostMapping(value = "/member/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.login(requestDto, response));
    }

    @PostMapping(value = "/member/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) throws BadRequestException {
        Member member = userDetails.getMember();
        memberService.logout(member);
        return ResponseEntity.ok().body(Map.of("msg", "로그아웃 완료"));
    }


    @PostMapping(value = "/findId/{canvasId}")
    public ResponseEntity<?> findId(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable String canvasId,@RequestBody ArrayList<String> inviteMemberNickname) throws InterruptedException {
        Member member = userDetails.getMember();
        memberService.findId(member,canvasId ,inviteMemberNickname);
        return ResponseEntity.ok().body(Map.of("success", "초대 성공"));

    }
}
