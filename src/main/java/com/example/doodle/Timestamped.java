package com.example.doodle;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@EnableMongoAuditing
@Document(collection = "timestamped")
public class Timestamped {

    @CreatedDate
    private LocalDateTime localDateTime;
}
