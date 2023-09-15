package com.example.doodle.Mongodb;

import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private MongoTemplate mongoTemplate;

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        try {
            Document result = mongoTemplate.getDb().runCommand(new Document("serverStatus", 1));
            if (result.getDouble("ok") == 1.0) {
                return ResponseEntity.ok("MongoDB 연결 상태: 정상");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MongoDB 연결 상태: 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("MongoDB 연결 상태: 실패");
        }
    }
}
