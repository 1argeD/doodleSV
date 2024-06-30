package com.example.doodle.Login;

import com.example.doodle.Exception.BadRequestException;
import com.example.doodle.Exception.CustomExceptionHandler;
import com.example.doodle.Exception.Exception;
import com.example.doodle.Member.Member;
import com.example.doodle.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        return member
                .map(UserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException("해당 아이디가 올바르지 않습니다. 다시 확인해 주세요."));
    }
}
