package ISTP.controller;

import ISTP.service.MemberService;
import ISTP.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
@Slf4j
public class AlarmController {

    private final MemberService memberService;
    private final RequestService requestService;

    /*@ResponseBody
    @GetMapping("/{memberId}")
    public Map<String, Object> myAlarmList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        boolean alarm = member.isAlarm();
        AlarmStatus alarmStatus = member.getAlarmStatus();
        List<Request> allByBloodType = requestService.findAllByBloodTypeExcludingMemberRequests(member.getMyBloodType(), member);
        List<AlarmSummaryDto> alarmDtoList = new ArrayList<>();
        for (Request request : allByBloodType) {
            AlarmSummaryDto alarmDto = new AlarmSummaryDto(request);
            alarmDtoList.add(alarmDto);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("alarmStatus", alarmStatus);
        result.put("alarmList", alarmDtoList);
        return result;
    }
    @PostMapping("/{memberId}/setting")
    public Boolean myAlarmSetting(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        memberService.changeAlarm(member);
        return member.isAlarm();
    }
    public AlarmStatus myAlarmSetting(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        memberService.changeAlarm(member);
        return member.getAlarmStatus();
    }*/
}
