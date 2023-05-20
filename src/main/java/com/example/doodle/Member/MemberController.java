package com.example.doodle.Member;

import com.example.doodle.Member.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private MemberService memberService;

    @PostMapping("/member/sign")
    public ResponseEntity<?> signup(MemberRequestDto memberRequestDto){
        return ResponseEntity.ok().body("asd");
    }
}
