package com.example.doodle.Canvas.Dto;
import lombok.Data;

import java.util.ArrayList;


@Data
public class CanvasRequestDto {
    private String canvasTitle;
    private ArrayList<String> with;
}
