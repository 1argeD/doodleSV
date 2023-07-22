package com.example.doodle.Canvas.Dto;

import com.example.doodle.Canvas.Canvas;
import lombok.*;

import java.util.ArrayList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CanvasResponseDto {
    private String id;
    private String canvasTitle;
    private ArrayList<String> with;

    public static CanvasResponseDto CanvasResponseDtoBuilder(Canvas canvas) {
        return CanvasResponseDto.builder()
                .id(canvas.getId())
                .canvasTitle(canvas.getCanvasTitle())
                .with(canvas.getWith())
                .build();
    }
}
