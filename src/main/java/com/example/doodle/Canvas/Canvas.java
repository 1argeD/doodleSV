package com.example.doodle.Canvas;

import com.example.doodle.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@Document(collection = "canvas")
@Builder
public class Canvas extends Timestamped {

    @Id
    private String id;
    @Field("maker")
    private String maker;
    @Field("canvas_title")
    private String canvasTitle;
    @Field("with")
    private ArrayList<String> with;
    @Field("date")
    private String date;

    void setCanvas_title(String put_canvas_title) {
        this.canvasTitle = put_canvas_title;
    }

    void invite(ArrayList<String> yous) {
        if(with==null) {
            with = new ArrayList<>();
            this.with.addAll(yous);
        } else {
            this.with.addAll(yous);
        }
    }
}
