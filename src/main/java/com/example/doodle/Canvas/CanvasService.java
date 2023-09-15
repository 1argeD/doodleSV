package com.example.doodle.Canvas;

import com.example.doodle.Canvas.Dto.CanvasRequestDto;
import com.example.doodle.Canvas.Dto.CanvasResponseDto;
import com.example.doodle.Member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class CanvasService {
    private final CanvasRepository canvasRepository;

    @Transactional
    public CanvasResponseDto makeCanvas(Optional<Member> member, CanvasRequestDto requestDto) {
        boolean isMember = member.isPresent();
        String maker = null;
        if(isMember) {
            maker = member.get().getId();
        }

        Canvas canvas = new Canvas.CanvasBuilder()
                .maker(maker)
                .canvasTitle(requestDto.getCanvasTitle())
                .with(requestDto.getWith())
                .build();

        canvasRepository.save(canvas);

        return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
    }

    @Transactional
    public List<CanvasResponseDto> getCanvas(Optional<Member> member) {
        boolean isMember = member.isPresent();
        List<Canvas> canvas = null;
        if(isMember) {
            canvas = canvasRepository.findCanvasByMaker(member.get().getId());
        }
        
        if (canvas == null&&isMember) {
            canvas = canvasRepository.findCanvasByWithExists(member.get().getId());
        }

        return Objects.requireNonNull(canvas).stream()
                .map(CanvasResponseDto::CanvasResponseDtoBuilder)
                .collect(Collectors.toList());
    }

    @Transactional
    public CanvasResponseDto updateCanvas(Member member, CanvasRequestDto requestDto) {
        String maker = member.getId();
        Canvas canvas = canvasRepository.findCanvasByCanvasTitle(requestDto.getCanvasTitle());

        if (!maker.equals(canvas.getMaker())) {
            throw new IllegalArgumentException("방 메이커만 방제목을 수정할 수 있습니다.");
        }

        canvas.setCanvas_title(requestDto.getCanvasTitle());

        return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
    }

    @Transactional
    public void deleteCanvas(Optional<Member> member, String canvasTitle) {

        if (member.isEmpty()) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        List<Canvas> canvasList = canvasRepository.findCanvasByMaker(member.get().getId());

        int i = 0;
        for (Canvas canvas : canvasList) {
            if (canvas.getCanvasTitle().equals(canvasTitle)) {
                canvasRepository.deleteCanvasByCanvasTitle(canvas.getCanvasTitle());
                if (!member.get().getId().equals(canvas.getMaker())) {
                    throw new IllegalArgumentException("방 메이커만 삭제가 가능합니다.");
                }
            }
        }
    }

    @Transactional
    public CanvasResponseDto invite(Optional<Member> member, String canvasId, CanvasRequestDto requestDto) throws InterruptedException {

        Canvas canvas = canvasRepository.findCanvasById(canvasId);

        boolean isMember = member.isPresent();

        for(String inviteIdCheck: canvas.getWith()) {
            for(String requestIdList : requestDto.getWith()) {
                if(inviteIdCheck.equals(requestIdList)) {
                    throw new IllegalArgumentException("이미 초대 되어 있는 유저가 있습니다.");
                }
            }
        }

        if(isMember&&!member.get().getId().equals(canvas.getMaker())) {
            throw new IllegalArgumentException("메이커만 초대를 할 수 있습니다.");
        } else {
            canvas.invite(requestDto.getWith());
            canvasRepository.save(canvas);
        }
        return CanvasResponseDto.CanvasResponseDtoBuilder(canvas);
    }
}
