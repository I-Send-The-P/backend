package ISTP.controller;

import ISTP.dtos.bloodCenter.BloodCenterDTO;
import ISTP.dtos.request.RequestRe;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import ISTP.dtos.request.RequestDto;
import ISTP.dtos.request.RequestListDto;
import ISTP.service.AlarmService;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final MemberService memberService;
    //private final BloodCenterService bloodService;
    private final AlarmService alarmService;

    @GetMapping("/list") // 게시글 리스트
    public Page<RequestListDto> requestList(@PageableDefault(size = 10) Pageable pageable) {

        Page<Request> requests = requestService.findByDESC(pageable);
        Page<RequestListDto> requestDtoList = requests.
                map(r -> new RequestListDto(r));
        return requestDtoList;
    }

    @GetMapping("/{requestId}") // 게시글 리스트에서 하나 클릭해서 들어가기
    public RequestDto requestOne(@PathVariable Long requestId) {
        Request request = requestService.findByOneId(requestId);
        Member member = request.getMember();
        RequestDto requestDto = new RequestDto(request, member);

        return requestDto;
    }

    @PostMapping("/blood")// 게시글 올리기
    public Long bloodRequest(@Validated @RequestBody RequestRe request) {
        Member member = memberService.findById(1L);
        Request savedRequest = new Request(member, request.getSickness(), request.getTitle(), request.getContent(),
                LocalDateTime.now().plusDays(3), request.getNumber(), request.getHospital(), RequestStatus.신청,
                BloodType.A_PLUS, request.getRelationship(), request.getRequests_blood_type(), request.getAddress());
        // 혈액형 고정, 기간 몰라서 3일로 고정함

        Long savedId = requestService.save(savedRequest);

        if(savedRequest.getAddress().equals("전체")) { // 전체 선택하면 전체의 사람들한테 보내짐
            List<Member> allByMemberBloodType = requestService.findAllByMemberBloodType(savedRequest.getBloodType());
            for (Member m : allByMemberBloodType) {
                System.out.println("전체 = " + m.getLoginId()); // 나중에 문자로 교체, 지금은 돈들어가니 안해놓음
            }
        }
        else { // 전체가 아니라 서울, 인천 이렇게 입력하면 해당 지역에 사는 사람만 알림 가기
            List<Member> regionByMemberBloodType = requestService.findRegionByMemberBloodType(savedRequest.getAddress(), savedRequest.getBloodType());
            for (Member m : regionByMemberBloodType) {
                System.out.println("지역만 = " + m.getLoginId()); // 나중에 문자로 교체, 지금은 돈들어가니 안해놓음
            }
        }

        //병원명으로 주소 어딘지 알수있게 Hospital 수정해야할듯?
        //=========규혁 추가==========
        alarmService.createAlarmForMember(member, savedRequest);
        return savedId;
    }

    @PutMapping("/status/{requestId}") // 신청중 -> 진행중 버튼
    public void request_status(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus(request);
    }

    @PutMapping("/finish/{requestId}") // 진행중 -> 완료 버튼
    public void update_finish(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus2(request);
    }

    @PutMapping("/request/{requestId}") // 취소했을 시 -> 다시 처음으로 신청버튼
    public void update_request(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus3(request);
    }

    @DeleteMapping("/delete/{requestId}") // 글 삭제
    public void delete_request(@PathVariable Long requestId) {
        requestService.delete(requestId);
    }

    /*@GetMapping("api")
    public List<BloodCenterDTO> api() throws Exception{
        return bloodService.API();
    }*/
}


