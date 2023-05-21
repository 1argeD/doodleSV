package com.example.doodle.Member;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends MongoRepository<Member, ObjectId> {
   Optional<Member> findByEmail(String email);
   boolean existsByEmail(String email);
   boolean existsByNickname(String nickname);

}
