package com.example.doodle.Canvas;

import com.example.doodle.Canvas.Dto.CanvasRequestDto;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class CanvasService {
    private final CanvasRepository canvasRepository;

    @Transactional
    public CanvasResponseDto makeCanvas(Optional<Member> member, CanvasRequestDto requestDto) {
        String maker = member.get().getId();

        ArrayList<String> with = new ArrayList<>();
        with.add(requestDto.getWith());

        Canvas canvas = new Canvas.CanvasBuilder()
                .maker(maker)
                .canvasTitle(requestDto.getCanvasTitle())
                .with(with)
                .build();

        canvasRepository.save(canvas);

        return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
    }

    @Transactional
    public List<CanvasResponseDto> getCanvas(Optional<Member> member) {

        List<Canvas> canvas = canvasRepository.findCanvasByMaker(member.get().getId());


        if(canvas==null) {
            canvas = canvasRepository.findCanvasByWithExists(member.get().getId());
        }

        return canvas.stream()
                .map(CanvasResponseDto :: CanvasResponseDtoBuilder)
                .collect(Collectors.toList());
    }

    @Transactional
    public CanvasResponseDto updateCanvas(Member member, CanvasRequestDto requestDto) {
        String maker = member.getId();
        Canvas canvas = canvasRepository.findCanvasByCanvasTitle(requestDto.getCanvasTitle());

        if(!maker.equals(canvas.getMaker())) {
            throw new IllegalArgumentException("방 메이커만 방제목을 수정할 수 있습니다.");
        }

        canvas.setCanvas_title(requestDto.getCanvasTitle());

        return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
    }

    @Transactional
    public void deleteCanvas(Optional<Member> member, String canvasTitle) {

        if(member.isEmpty()) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        List<Canvas> canvasList = canvasRepository.findCanvasByMaker(member.get().getId());

        log.info(canvasTitle);
        int i = 0;
        for(Canvas canvas : canvasList) {
            log.info(canvas.getCanvasTitle());
                if(canvas.getCanvasTitle().equals(canvasTitle)) {
                    canvasRepository.deleteCanvasByCanvasTitle(canvas.getCanvasTitle());
                    if(!member.get().getId().equals(canvas.getMaker())) {
                        throw new IllegalArgumentException("방 메이커만 삭제가 가능합니다.");
                }
                }
        }

//        throw new IllegalArgumentException("그런 이름의 canvas가 존재하지 않습니다.");

    }

    @Transactional
    public CanvasResponseDto invite(Optional<Member> member, CanvasRequestDto requestDto) {

        List<Canvas> canvasList = canvasRepository.findCanvasByMaker(member.get().getId());
        ArrayList<String> with = new ArrayList<>();

        for(Canvas canvas : canvasList) {
            if(canvas.getCanvasTitle().equals(requestDto.getCanvasTitle())) {
                if(requestDto.getWith().length()>1) {
                    canvas.invite(with);
                } else {
                    canvas.invite(requestDto.getWith());
                }
                return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
            }
        }
        return null;
    }
}
