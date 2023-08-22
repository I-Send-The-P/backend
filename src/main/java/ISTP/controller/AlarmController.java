package ISTP.controller;

import ISTP.dtos.member.AlarmSummaryDto;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Alarm;
import ISTP.domain.member.Member;
import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
@Slf4j
public class AlarmController {

    private final MemberService memberService;
    private final RequestService requestService;

    @ResponseBody
    @GetMapping("/{memberId}")
    public Map<String, Object> myAlarmList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        Alarm alarm = member.getAlarm();
        List<Request> allByBloodType = requestService.findAllByBloodTypeExcludingMemberRequests(member.getMyBloodType(), member);
        List<AlarmSummaryDto> alarmDtoList = new ArrayList<>();
        for (Request request : allByBloodType) {
            AlarmSummaryDto alarmDto = new AlarmSummaryDto(request);
            alarmDtoList.add(alarmDto);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("alarmStatus", alarm);
        result.put("alarmList", alarmDtoList);
        return result;
    }

    //호출 될 때마다 알람 온 오프로 되는 기능
    @PostMapping("/{memberId}/setting")
    public Alarm myAlarmSetting(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        memberService.changeAlarm(member);
        return member.getAlarm();
    }
}
