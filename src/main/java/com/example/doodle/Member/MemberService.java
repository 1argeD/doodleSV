package com.example.doodle.Member;

import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Canvas.CanvasService;
import com.example.doodle.Canvas.Dto.CanvasRequestDto;
import com.example.doodle.Login.Dto.LoginRequestDto;
import com.example.doodle.Login.Dto.LoginResponseDto;
import com.example.doodle.Login.Dto.SignupRequestDto;
import com.example.doodle.Login.JWT.JwtFilter;
import com.example.doodle.Login.JWT.JwtProvider;
import com.example.doodle.Login.RefreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.hibernate.boot.model.source.internal.hbm.XmlElementMetadata;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final CanvasService canvasService;
    private final CanvasRepository canvasRepository;


    public boolean checkEmailIsDuplication(String email) {
        boolean isDuplication = memberRepository.existsByEmail(email);
            if (isDuplication) {
                throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        return false;
    }

    public boolean checkNicknameDuplication(String nickname) {
        boolean isDuplication = memberRepository.existsByNickname(nickname);
        if (isDuplication) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        return false;
    }

    public void checkPassword(String password, String encodingPassword) {
        boolean isSome = passwordEncoder.matches(password, encodingPassword);
        if (!isSome) {
            throw new IllegalArgumentException("비밀번호를 확인하세요.");
        }
    }

    public void signup(SignupRequestDto requestDto) {
        checkEmailIsDuplication(requestDto.getEmail());
        String encodingPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), encodingPassword);
        memberRepository.save(member);
    }

    public void tokenToHeaders(String authorizationToken, String refreshToken, HttpServletResponse response) {
        response.addHeader("Authorization", JwtFilter.BEARER_PREFIX + authorizationToken);
        response.addHeader("RefreshToken", refreshToken);
    }

    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디를 확인하세요."));
        checkPassword(requestDto.getPassword(), member.getPassword());
        String accessToken = jwtProvider.createAuthorizationToken(member.getEmail(), member.getRole());
        String refreshToken = jwtProvider.createRefreshToken(member, member.getRole());
        tokenToHeaders(accessToken, refreshToken, response);
        return new LoginResponseDto(member.getId(),member.getNickname(),member.getEmail(), true);
    }

    public void logout(Member member) {
        refreshTokenRepository.deleteByMember(member);
    }

    public void findId(Member member, String canvasId,ArrayList<String> inviteMemberNickname) throws InterruptedException {

        ArrayList<String> inviteMemberIdList = new ArrayList<>();
        CanvasRequestDto canvasRequestDto = new CanvasRequestDto();
        Optional<Member> maker = Optional.ofNullable(member);
        boolean isMember = memberRepository.existsById(Objects.requireNonNull(member).getId());
        if(!isMember) {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다 회원 정보를 확인해 주세요");
        }
        for(String nickname : inviteMemberNickname) {
            Optional<Member> inviteMemberId =  memberRepository.findMemberByNickname(nickname);
            inviteMemberId.ifPresent(value -> inviteMemberIdList.add(value.getId()));
        }

        canvasRequestDto.setWith(inviteMemberIdList);
        canvasRequestDto.setCanvasTitle(canvasRepository.findCanvasById(canvasId).getCanvasTitle());

        canvasService.invite(maker,canvasId ,canvasRequestDto);
    }

}
