package com.example.doodle.Pen;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Login.UserDetailsImpl;
import com.example.doodle.Member.Member;
import com.example.doodle.Pen.DTO.PenRequestDTO;
import com.example.doodle.Pen.DTO.PenResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
public class PenController {
    private final PenService penService;

    @PostMapping(value = "/canvas/pen")
    public ResponseEntity<?> createPen (@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PenRequestDTO penRequestDTO, @PathVariable String canvas_id) {
        Member member = userDetails.getMember();
        PenResponseDTO penResponseDTO = penService.create(member, penRequestDTO, canvas_id);
        return ResponseEntity.ok().body(penResponseDTO);
    }

    @PutMapping(value = "canvas/pen-put")
    public List<String> penPut(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return null;
    }
}
