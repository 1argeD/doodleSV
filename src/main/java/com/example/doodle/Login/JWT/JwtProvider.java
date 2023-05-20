package com.example.doodle.Login.JWT;


import com.example.doodle.Login.RefreshToken.RefreshTokenRepository;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;


@Component
public class JwtProvider {
    private final Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    public JwtProvider(@Value("${jwt.secret-key}") String SECRET_KEY,
                       RefreshTokenRepository refreshTokenRepository,
                       UserDetailsService userDetailsService) {
        byte[]keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        this.key = Keys.
    }
}
