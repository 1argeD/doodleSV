package com.example.doodle.Pen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@Document(collection = "pen")
@Builder
public class Pen {
    private String canvas_id;
    private String member_id;
    private String color;
    private List<String> spot;

    void penUpdate(List<String> spot) {
        this.spot = spot;
    }
}
