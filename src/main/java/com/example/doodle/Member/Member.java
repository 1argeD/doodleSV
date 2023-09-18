package com.example.doodle.Member;

import com.example.doodle.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@ToString
@Getter
@AllArgsConstructor
@Document(collection = "members")
public class Member extends Timestamped {

    @Id
    private String id;
    @Field("nickname")
    private String nickname;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("role")
    private String role;

    public Member() {
        super();
    }

    public String getId() {
        return id;
    }

    @Builder
    public Member(String email, String nickname, String password) {
        super();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        role = "ROLE_USER";
    }

}
