package com.example.doodle.Canvas;

import com.example.doodle.Member.Member;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableMongoRepositories(basePackages = "canvas")
public interface CanvasRepository extends MongoRepository<Canvas, String> {
   List<Canvas> findCanvasByMaker(String maker);

   Canvas findCanvasById(String canvasId);

    List<Canvas> findCanvasByWithExists(String withId);
    Canvas findCanvasByCanvasTitle(String canvas_title);

    Canvas deleteCanvasByCanvasTitle(String canvasTitle);
}
