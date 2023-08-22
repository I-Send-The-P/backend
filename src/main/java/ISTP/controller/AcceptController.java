package ISTP.controller;

import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.bloodDonation.accept.AcceptStatus;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.service.AcceptService;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accept")
public class AcceptController {

    private final AcceptService acceptService;
    private final RequestService requestService;
    private final MemberService memberService;

    @PostMapping("/{requestId}") // 글에서 수락버튼 누르는 것
    public Long accept(@PathVariable Long requestId) {
        Request request = requestService.findById(requestId);
        Member member = memberService.findById(1L);
        Accept accept = new Accept(member, request, AcceptStatus.수락);
        Long savedId = acceptService.save(accept);
        return savedId;
    }

    @PostMapping("/change_finish/{requestId}") // 헌혈 후 완료버튼 누르기
    public void finish(@PathVariable Long requestId) {
        Accept accept = acceptService.findById(requestId);
        accept.update_finish();
    }

    @PostMapping("/change_cancel/{requestId}") // 수락했는데 취소하는것
    public void cancel(@PathVariable Long requestId) {
        Accept accept = acceptService.findById(requestId);
        accept.update_cancel();
    }

}
