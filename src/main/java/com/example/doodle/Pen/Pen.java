package com.example.doodle.Pen;

import com.example.doodle.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@Document(collection = "pen")
@Builder
public class Pen extends Timestamped {
    private String canvas_id;
    private String member_id;
    private String color;
    private List<String> spot;
    private String date;

    void penUpdate(List<String> spot) {
        this.spot = spot;
    }
}
