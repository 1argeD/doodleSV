package com.example.doodle.Login.RefreshToken;

import com.example.doodle.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document( collection = "refreshToken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String tokenValue;

    @OneToOne
    private Member member;

    public void updateTokenValue(String token) {
        this.tokenValue =token;
    }
}
