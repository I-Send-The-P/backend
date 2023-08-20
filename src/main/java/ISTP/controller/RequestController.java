package ISTP.controller;

import ISTP.Dtos.Requestrequest;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/request/blood")
    public Long bloodRequest(@RequestBody Requestrequest request) {
        // Member member = new Member("aaa", "aaa");

        Request savedRequest = new Request(new Member(), request.getSickness(), request.getContent(), LocalDate.now().plusDays(3),
                request.getNumber(), request.getHospital(), RequestStatus.신청, BloodType.A_PLUS, // enum 받는법
                 request.getRelationship(), request.getRequests_blood_type());

        Long savedId = requestService.save(savedRequest);

        return savedId;
    }

    @PostMapping("request/status/{requestId}")
    public void request_status(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        request.update_status();
    }

    @PostMapping("request/finish/{requestId}")
    public void update_finish(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        request.update_finish();
    }

    @PostMapping("request/request/{requestId}")
    public void update_request(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        request.update_request();
    }

    @DeleteMapping("delete/request/{requestId}")
    public void delete_request(@PathVariable Long requestId) {
        requestService.delete(requestId);
    }
}
