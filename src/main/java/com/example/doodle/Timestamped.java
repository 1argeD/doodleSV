package com.example.doodle;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Document(collection = "timestamped")
public class Timestamped {

    @Id
    private String  id;
    private Long seq;

    @CreatedDate
    private LocalDateTime localDateTime;
}
