package com.example.doodle.Pen;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableMongoRepositories(basePackages = "pen")
public interface PenRepository extends CrudRepository<Pen, String> {
    List<Pen> findPenByCanvasId(String canvasId);
}
