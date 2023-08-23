package ISTP.service;

import ISTP.domain.MemberAlarm;
import ISTP.domain.alarm.Alarm;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.repository.AlarmRepository;
import ISTP.repository.MemberAlarmRepository;
import ISTP.repository.MemberRepository;
import ISTP.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final MemberAlarmRepository memberAlarmRepository;
    private final RequestRepository requestRepository;

    @Transactional //여기서 받는 멤버는 요청한 멤버
    public Long createAlarmForMember(Member requestMember, Request request) {
        Alarm alarm = new Alarm(requestMember, request.getTitle(), request.getId(),  false);
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


    //한 멤버가 지금까지 받은 알람 요청서 조회
    public List<Request> findAllAccept(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        List<MemberAlarm> allByAcceptMember = memberAlarmRepository.findAllByAcceptMemberOrderByCreateDateDesc(member);
        List<Request> requests = new ArrayList<>();
        for (MemberAlarm memberAlarm : allByAcceptMember) {
            Alarm alarm = memberAlarm.getAlarm();
            Request request = requestRepository.findById(alarm.getRequestId()).get();
            requests.add(request);
        }
        return requests;
    }

    //알람 페이지에서 누르면 isRead-> true 로 변경


}
