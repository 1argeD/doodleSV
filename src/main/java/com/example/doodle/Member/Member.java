package com.example.doodle.Member;

import com.example.doodle.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document( collection = "members")
public class Member extends Timestamped {
    @Transient
    public static final String SEQUENCE_NAME = "posts_sequences";

    @Id
    private Long  member_id;

    public void setMember_id(Long  id) {
        this.member_id = id;
    }

    @Column
    private String nickname;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String role;


    @Builder
    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        role = "ROLE_USER";
    }
}
