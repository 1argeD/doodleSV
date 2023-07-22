package com.example.doodle.Canvas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@Document(collection = "canvas")
@Builder
public class Canvas {

    @Id
    private String id;
    @Field("maker")
    private String maker;
    @Field("canvas_title")
    private String canvasTitle;
    @Field("with")
    private ArrayList<String> with;

    void setCanvas_title(String put_canvas_title) {
        this.canvasTitle = put_canvas_title;
    }

    void invite(String you) {
        this.with.add(you);
    }

    void invite(ArrayList<String> yous) {
        this.with.addAll(yous);
    }
}
