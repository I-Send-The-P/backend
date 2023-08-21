package ISTP.controller;

import ISTP.dtos.member.AlarmDto;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Alarm;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
@Slf4j
public class AlarmController {

    private final MemberService memberService;
    private final RequestService requestService;

    //내 알림창 들어갔을 때 보이는 알람 리스트
    @ResponseBody
    @GetMapping("/{memberId}")
    public List<AlarmDto> myAlarmList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        List<Request> allByBloodType = requestService.findAllByBloodTypeExcludingMemberRequests(member.getMyBloodType(), member);
        List<AlarmDto> alarmDtoList = new ArrayList<>();
        for (Request request : allByBloodType) {
            AlarmDto alarmDto = new AlarmDto(request);
            alarmDtoList.add(alarmDto);
        }
        return alarmDtoList;
    }

    //호출 될 때마다 알람 온 오프로 되는 기능
    @PostMapping("/{memberId}/setting")
    public Alarm myAlarmSetting(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        memberService.changeAlarm(member);
        return member.getAlarm();
    }
}
