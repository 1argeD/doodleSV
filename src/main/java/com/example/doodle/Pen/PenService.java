package com.example.doodle.Pen;

import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Member.Member;
import com.example.doodle.Member.MemberRepository;
import com.example.doodle.Pen.DTO.PenRequestDTO;
import com.example.doodle.Pen.DTO.PenResponseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PenService {
    private final MemberRepository memberRepository;

    public PenResponseDTO create(Member member, PenRequestDTO penRequestDTO, String canvas_id) {
       Optional<?> isMemberId = memberRepository.findById(member.getId());
        if(isMemberId.isPresent()) {
            throw new IllegalArgumentException("아이디가 없습니다.");
        }

        Pen pen = new Pen.PenBuilder()
                .pen_id(penRequestDTO.getPen_id())
                .canvas_id(canvas_id)
                .color(penRequestDTO.getColor())
                .spot(penRequestDTO.getSpot())
                .build();

        return PenResponseDTO.PenResponseDTOBulider(pen, canvas_id);
    }

}
