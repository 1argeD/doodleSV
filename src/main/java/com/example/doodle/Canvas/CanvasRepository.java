package com.example.doodle.Canvas;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableMongoRepositories(basePackages = "canvas")
public interface CanvasRepository extends MongoRepository<Canvas, String> {
   List<Canvas> findCanvasByMaker(String maker);

   Canvas findCanvasById(String canvasId);

    List<Canvas> findCanvasByWithIs(String withId);
    List<Canvas> findCanvasByWithExists(String withId);
    Canvas findCanvasByCanvasTitle(String canvas_title);
    void deleteById(String canvasId);
}
