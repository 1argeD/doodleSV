package com.example.doodle.Login.RefreshToken;

import com.example.doodle.Member.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@EnableMongoRepositories(basePackages = "refresh_token")
@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMember(Member member);
    void deleteByMember(Member member);
}
