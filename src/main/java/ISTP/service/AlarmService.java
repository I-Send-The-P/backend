package ISTP.service;

import ISTP.domain.MemberAlarm;
import ISTP.domain.alarm.Alarm;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.repository.AlarmRepository;
import ISTP.repository.MemberAlarmRepository;
import ISTP.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final MemberAlarmRepository memberAlarmRepository;

    @Transactional //여기서 받는 멤버는 요청한 멤버
    public Long createAlarmForMember(Member requestMember, Request request) {
        Alarm alarm = new Alarm(requestMember, request.getTitle(), false);
        List<Member> acceptAlarmMember = memberRepository.
                findAllByMyBloodTypeAndAlarmStatusAndAddress
                        (request.getBloodType(), true, "인천시");
        /**
         * 병원 명이 아니라 주소 받아야하는데 그게 지금 구현이 안되어있음 회의해야함
         */
        System.out.println("acceptAlarmMember = " + acceptAlarmMember.size());
        for (Member acceptMember : acceptAlarmMember) {
            MemberAlarm memberAlarm = new MemberAlarm(acceptMember, alarm);
            alarm.addMemberAlarms(memberAlarm);
            log.info("{}에게 알람 요청", acceptMember.getNickname());
            memberAlarmRepository.save(memberAlarm);
        }
        alarmRepository.save(alarm);
        log.info("{} {} 알람 생성", alarm.getId(), alarm.getContent());
        return alarm.getId();
    }




}
