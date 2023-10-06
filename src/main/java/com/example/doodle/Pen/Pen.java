package com.example.doodle.Pen;

import com.example.doodle.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@Document(collection = "pen")
@Builder
public class Pen implements Serializable {
    @Id
    private String id;
    @Field("canvasId")
    private String canvasId;
    @Field("memberId")
    private String memberId;
    @Field("color")
    private String color;
    @Field("spot")
    private List<String> spot;


    void penUpdate(List<String> spot) {
        this.spot = spot;
    }
}
