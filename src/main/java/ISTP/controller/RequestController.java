package ISTP.controller;

import ISTP.Dtos.request.RequestDto;
import ISTP.Dtos.request.RequestListDto;
import ISTP.Dtos.request.RequestRe;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final MemberService memberService;

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
    public Long bloodRequest(@RequestBody RequestRe request) {
        Member member = memberService.findById(1L);

        Request savedRequest = new Request(member, request.getSickness(), request.getTitle(), request.getContent(),
                LocalDateTime.now().plusDays(3), request.getNumber(), request.getHospital(), RequestStatus.신청,
                BloodType.A_PLUS, request.getRelationship(), request.getRequests_blood_type());

        Long savedId = requestService.save(savedRequest);

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
}
