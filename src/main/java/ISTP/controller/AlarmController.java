package ISTP.controller;

import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.dtos.alarm.AlarmSummaryDto;
import ISTP.service.AlarmService;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
@Slf4j
public class AlarmController {

    private final MemberService memberService;
    private final AlarmService alarmService;

    @ResponseBody
    @GetMapping("/{memberId}")
    public Map<String, Object> myAlarmList(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        boolean alarmStatus = member.isAlarmStatus(); //알람 수신 상태

        List<Request> allAccept = alarmService.findAllAccept(memberId);
        List<AlarmSummaryDto> alarmDtoList = new ArrayList<>();
        for (Request request : allAccept) {
            AlarmSummaryDto alarmDto = new AlarmSummaryDto(request);
            alarmDtoList.add(alarmDto);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("alarmStatus", alarmStatus);
        result.put("alarmList", alarmDtoList);
        return result;
    }

    //알람설졍 변경
    @PostMapping("/{memberId}/setting")
    public Boolean myAlarmSetting(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId);
        memberService.changeAlarm(member);
        return member.isAlarmStatus();
    }


}
