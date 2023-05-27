package com.example.doodle.Login.RefreshToken;

import com.example.doodle.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document( collection = "refreshToken")
@Getter
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String id;

    private String tokenValue;
    @DBRef(lazy = true)//OneToOne 매핑
    private Member member;

    public void updateTokenValue(String token) {
        this.tokenValue =token;
    }
}
