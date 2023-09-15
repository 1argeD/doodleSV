package com.example.doodle.Pen.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PenRequestDTO {
    private String pen_id;
    private String color;
    private List<String> spot;
}
