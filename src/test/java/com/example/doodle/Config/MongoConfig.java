package com.example.doodle.Config;

import com.mongodb.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    private final String URL = "MongoDB://localhost:27017";
    private final String DATABASE = "paint";

    @Bean
    public MongoDatabase mongoDatabase() {
        MongoClient mongoClient = MongoClients.create(URL);
        MongoDatabase database = mongoClient.getDatabase(DATABASE);
        return database;
    }

}
