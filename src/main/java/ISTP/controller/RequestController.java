package ISTP.controller;

import ISTP.dtos.RequestDto;
import ISTP.dtos.Requestrequest;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final MemberService memberService;

    @GetMapping("request_list")
    public List<RequestDto> requestList() {
        List<Request> requests = requestService.findAll();
        List<RequestDto> requestDtoList = requests.stream().
                map(r -> new RequestDto(r))
                .collect(Collectors.toList());

        return requestDtoList;
    }

    @PostMapping("request/blood")
    public Long bloodRequest(@RequestBody Requestrequest request) {
        Member member = memberService.findById(1L);

        Request savedRequest = new Request(member, request.getSickness(), request.getTitle(), request.getContent(),
                LocalDate.now().plusDays(3), request.getNumber(), request.getHospital(), RequestStatus.신청,
                BloodType.A_PLUS, request.getRelationship(), request.getRequests_blood_type());

        Long savedId = requestService.save(savedRequest);

        return savedId;
    }

    @PutMapping("request/status/{requestId}")
    public void request_status(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus(request);
    }

    @PutMapping("request/finish/{requestId}")
    public void update_finish(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus2(request);
    }

    @PutMapping("request/request/{requestId}")
    public void update_request(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        requestService.changeStatus3(request);
    }

    @DeleteMapping("delete/request/{requestId}")
    public void delete_request(@PathVariable Long requestId) {
        requestService.delete(requestId);
    }
}
