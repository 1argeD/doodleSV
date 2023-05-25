package com.example.doodle.Login.RefreshToken;

import com.example.doodle.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document( collection = "refreshToken")
public class RefreshToken {
    @Id
    private String id;

    private String tokenValue;

    private Member member;

    public void updateTokenValue(String token) {
        this.tokenValue =token;
    }
}
