package com.example.doodle.Member;

import com.example.doodle.Login.Dto.LoginRequestDto;
import com.example.doodle.Login.Dto.LoginResponseDto;
import com.example.doodle.Login.Dto.SignupRequestDto;
import com.example.doodle.Login.JWT.JwtFilter;
import com.example.doodle.Login.JWT.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;


    public void checkEmailIsDuplication(String email) {
        boolean isDuplication = memberRepository.existsByEmail(email);
        if(isDuplication) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public void checkNicknameDuplication(String nickname) {
        boolean isDuplication  = memberRepository.existsByNickname(nickname);
        if(isDuplication) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    public void checkPassword(String password, String encodingPassword) {
        boolean isSome = passwordEncoder.matches(password, encodingPassword);
        if(!isSome) {
            throw new IllegalArgumentException("비밀번호를 확인하세요.");
        }
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        checkEmailIsDuplication(requestDto.getEmail());
        String encodingPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), encodingPassword);
        memberRepository.save(member);
    }

    public void tokenToHeaders(String authorizationToken, String refreshToken, HttpServletResponse response) {
        response.addHeader("Authorization", JwtFilter.BEARER_PREFIX +authorizationToken);
        response.addHeader("RefreshToken", refreshToken);
    }


    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디를 확인하세요."));
        checkPassword(requestDto.getPassword(), member.getPassword());
        String accessToken = jwtProvider.creatAuthorizationToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member, member.getRole());
        tokenToHeaders(accessToken, refreshToken, response);
        return new LoginResponseDto(member.getMember_id(), member.getNickname(), true);
    }

}
