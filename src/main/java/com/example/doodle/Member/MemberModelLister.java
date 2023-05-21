package com.example.doodle.Member;

import com.example.doodle.Member.Member;
import com.example.doodle.Mongodb.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberModelLister extends AbstractMongoEventListener<Member> {
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Member>event) {
        event.getSource().setMember_id(sequenceGeneratorService.generatorSequence(Member.SEQUENCE_NAME));
    }
}
