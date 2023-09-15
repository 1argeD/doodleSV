package com.example.doodle.Pen;


import com.example.doodle.Member.Member;
import com.example.doodle.Member.MemberRepository;
import com.example.doodle.Pen.DTO.PenRequestDTO;
import com.example.doodle.Pen.DTO.PenResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PenService {
    private final MemberRepository memberRepository;

    List<Pen> penList = new ArrayList<>();

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
        penList.add(pen);
        return PenResponseDTO.PenResponseDTOBulider(pen, canvas_id);
    }

    public List<String> penUpdate(String member_id, List<String> spot) {
        return null;
    }

}
