package com.example.doodle.Member;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, Long> {
   Optional<Member> findByEmail(String email);
   boolean existsByEmail(String email);
   boolean existsByNickname(String nickname);
}
