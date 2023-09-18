package com.example.doodle.Pen.DTO;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Pen.Pen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PenResponseDTO {
    String color;
    List<String> spot;
    String canvas_id;

    public static PenResponseDTO PenResponseDTOBulider(Pen pen, String canvas_id) {
        return PenResponseDTO.builder()
                .canvas_id(canvas_id)
                .color(pen.getColor())
                .spot(pen.getSpot())
                .build();
    }
}
