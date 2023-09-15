package com.example.doodle.Pen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@Document(collection = "pen")
@Builder
public class Pen {
    @Id
    private String pen_id;
    private String canvas_id;
    private String color;
    private List<String> spot;
}
