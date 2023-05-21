package com.example.doodle.Login.JWT;


import com.example.doodle.Login.RefreshToken.RefreshToken;
import com.example.doodle.Login.RefreshToken.RefreshTokenRepository;
import com.example.doodle.Member.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserDetailsService userDetailsService;

    public JwtProvider(@Value("${jwt.secret-key}") String SECRET_KEY,
                       RefreshTokenRepository refreshTokenRepository,
                       UserDetailsService userDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String MemberEmail, String roles, Long TokenInvalidedTime) {
        Claims claims = Jwts.claims().setSubject(MemberEmail);
        claims.put("Roles", roles);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + TokenInvalidedTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch ( SecurityException | MalformedJwtException e) {
            log.info("Jwt claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String creatAuthorizationToken(String memberEmail, String role) {
        Long tokenInvalidedTime = 10000L*60*60;
        return this.createToken(memberEmail, role, tokenInvalidedTime);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String memberEmail = claims.getSubject();
        UserDetails principal = userDetailsService.loadUserByUsername(memberEmail);
        return new UsernamePasswordAuthenticationToken(principal, "", null);
    }

    private Long getExpiredTime(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();

        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }


    public String createRefreshToken(Member member, String role) {
        long tokenInvalidedTime = 1000*60*60*24;
        String refreshToken = this.createToken(member.getEmail(), role, tokenInvalidedTime);
        RefreshToken refreshTokenObj = refreshTokenRepository.findByMember(member)
                .orElse(RefreshToken.builder()
                        .member(member)
                        .build());
        refreshTokenObj.updateTokenValue(refreshToken);
        return refreshToken;
    }


    /*리 이슈 문제떄 사용할 로직*/
//    public void checkRefreshToken(String email, String refreshToken) throws BadRequestException {
//        if(!refreshToken.equals()) {
//            throw new BadRequestException("토큰이 만료 되었습니다.");
//        }
//    }


}
