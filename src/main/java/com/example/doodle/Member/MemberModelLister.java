package com.example.doodle.Member;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberModelLister extends AbstractMongoEventListener<Member> {
}
