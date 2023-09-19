package com.example.doodle.Login.JWT;

import com.example.doodle.Login.JWT.JwtFilter;
import com.example.doodle.Login.JWT.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JWTConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtProvider jwtProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtFilter customJwtFilter = new JwtFilter(jwtProvider);
        httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
