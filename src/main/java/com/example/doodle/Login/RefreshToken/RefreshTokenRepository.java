package com.example.doodle.Login.RefreshToken;

import com.example.doodle.Member.Member;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;


@Configuration
@EnableMongoRepositories(basePackages = "refresh_token")
@EnableAsync
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByMember(Member member);
    void deleteByMember(Member member);
}
