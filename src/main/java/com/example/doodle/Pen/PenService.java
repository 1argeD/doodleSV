package com.example.doodle.Pen;


import com.example.doodle.Canvas.Canvas;
import com.example.doodle.Canvas.CanvasRepository;
import com.example.doodle.Member.Member;
import com.example.doodle.Member.MemberRepository;
import com.example.doodle.Pen.DTO.PenRequestDTO;
import com.example.doodle.Pen.DTO.PenResponseDTO;

import com.example.doodle.Redis.RedisService;
import com.example.doodle.Redis.RedisUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;


import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PenService {
    private final PenRepository penRepository;
    private final RedisUtil redisUtil;
    private final RedisService redisService;
    private final MemberRepository memberRepository;
    private final CanvasRepository canvasRepository;
    private Pen findPen;
    private String inputedMemberId = "";

    List<Pen> penList = new ArrayList<>();

    public void penCreate(String memberId, String color, String spotInfo, String canvasId) {
        Member memberInfo = null;
        String[] s = spotInfo.split(",");

        ArrayList<String> spot = new ArrayList<>(Arrays.asList(s));
        PenRequestDTO penRequestDTO = new PenRequestDTO(color,spot);
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isPresent()){
            memberInfo = member.get();
        } else {
            throw new IllegalArgumentException("회원정보가 비었습니다");
        }
        create(memberInfo, penRequestDTO, canvasId);
    }

    public PenResponseDTO create(Member member, PenRequestDTO penRequestDTO, String canvasId) {
        String penUser = null;
        Optional<Member> findMember = Objects.requireNonNull(memberRepository).findById(member.getId());
        Canvas canvas = Objects.requireNonNull(canvasRepository).findCanvasById(canvasId);
        boolean isMember = findMember.isPresent();

        int inviteMemberCHKcount = 0;

        if (!isMember) {
            throw new IllegalArgumentException("아이디가 없습니다.");
        } else {
            String canvasMaker = canvas.getMaker();
            List<String> invitedMemberList = canvas.getWith();

            if(invitedMemberList==null) {
                findMember.get().getId().equals(canvasMaker);
                penUser = canvasMaker;
            } else {
                for (String inviteMember : invitedMemberList) {
                    if (findMember.get().getId().equals(canvasMaker)) {
                        penUser = canvasMaker;
                        continue;
                    } else if (inviteMember.equals(findMember.get().getId())) {
                        penUser = findMember.get().getId();
                        continue;
                    }

                    inviteMemberCHKcount++;

                    if (inviteMemberCHKcount >= invitedMemberList.size()) {
                        throw new IllegalArgumentException("초대되지 않은 맴버입니다.");
                    }
                }
            }

            Pen pen = new Pen.PenBuilder()
                    .canvasId(canvasId)
                    .memberId(penUser)
                    .color(penRequestDTO.getColor())
                    .spot(penRequestDTO.getSpot())
                    .build();

            penRepository.save(pen);
            redisService.setPenValue(pen, pen.getId());
            penList.add(pen);

            return PenResponseDTO.PenResponseDTOBulider(pen, canvasId);
        }
    }


    //아무리 생각해도 웹소켓으로 그림을 그릴때 업데이트 요청을 계속 보내게 되는데, 보낼떄 마다 for문으로 penList를 탐색하는 것은 비효율적
    //미리 memberid값으로 해당 pen을 찾아 저장해 놓고 해당 자원을 지속적으로 수정하게 구현해야함.
    //펜을 찾는 부분과 spot 수정 함수를 분리함.
    public Pen search(String memberId) {
        if(inputedMemberId.equals(memberId)) {
                return findPen;
        } else {
            for (Pen pen : penList) {
                if (pen.getMemberId().equals(memberId)) {
                    findPen = pen;
                    inputedMemberId = memberId;
                    return findPen;
                }
            }
        }
    return null;
    }

    public List<Pen> getPen(String memberId,String canvasId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {
            return penRepository.findPenByCanvasId(canvasId);
        } else {
            throw new IllegalArgumentException("없는 회원입니다.");
        }
    }

    public List<String> penUpdate(Pen pen, List<String> spot) {
        pen.penUpdate(spot);
        return pen.getSpot();
    }

    public void penDelete(String memberId, String memberNickname) {
        Optional<Member> memberCheckId = memberRepository.findById(memberId);
        Optional<Member> memberCheckNick = memberRepository.findMemberByNickname(memberNickname);

        boolean memberExistCheck = memberCheckId.isPresent();
        boolean memberNickExistCheck = memberCheckNick.isPresent();

        if(!memberExistCheck) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
        if(!memberNickExistCheck) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }

}
