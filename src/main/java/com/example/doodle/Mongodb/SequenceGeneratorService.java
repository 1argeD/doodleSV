package com.example.doodle.Mongodb;

import com.example.doodle.Timestamped;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@RequiredArgsConstructor
@Service
public class SequenceGeneratorService {
    private MongoOperations mongoOperations;

    public long generatorSequence(String seq) {
        Timestamped timestamped = mongoOperations.findAndModify(query(where("_id").is(seq)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                Timestamped.class);
        return !Objects.isNull(timestamped) ? timestamped.getSeq() : 1;
    }
}
