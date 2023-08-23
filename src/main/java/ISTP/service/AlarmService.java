package ISTP.service;

import ISTP.domain.MemberAlarm;
import ISTP.domain.alarm.Alarm;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import ISTP.dtos.alarm.AcceptAndIsReadDto;
import ISTP.repository.AlarmRepository;
import ISTP.repository.MemberAlarmRepository;
import ISTP.repository.MemberRepository;
import ISTP.repository.RequestRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final MemberAlarmRepository memberAlarmRepository;
    private final RequestRepository requestRepository;


    public Alarm findAlarmById(Long alarmId) {
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알람입니다"));
        log.info("아이디로 알람 찾기 {}", findAlarm);
        return findAlarm;
    }
    public MemberAlarm findMemberAlarmById(Long memberAlarmId) {
        MemberAlarm findMemberAlarm = memberAlarmRepository.findById(memberAlarmId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버알람입니다"));
        log.info("아이디로 멤버알람 찾기 {}", memberAlarmId);
        return findMemberAlarm;
    }

    @Transactional //여기서 받는 멤버는 요청한 멤버
    public Long createAlarmForMember(Member requestMember, Request request) {
        Alarm alarm = new Alarm(requestMember, request.getTitle(), request.getId());
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

<<<<<<< HEAD
=======

    //한 멤버가 지금까지 받은 알람 요청서 조회
    public List<AcceptAndIsReadDto> findAllAccept(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        List<MemberAlarm> allByAcceptMember = memberAlarmRepository.findAllByAcceptMemberOrderByCreateDateDesc(member);
        List<AcceptAndIsReadDto> acceptAndIsReadDtos = new ArrayList<>();
        for (MemberAlarm memberAlarm : allByAcceptMember) {
            Alarm alarm = memberAlarm.getAlarm();
            Request request = requestRepository.findById(alarm.getRequestId()).get();
            AcceptAndIsReadDto acceptAndIsReadDto = new AcceptAndIsReadDto(request, memberAlarm.isRead());
            acceptAndIsReadDtos.add(acceptAndIsReadDto);
        }
        return acceptAndIsReadDtos;
    }


    //알람 페이지에서 누르면 isRead-> true 로 변경
    @Transactional
    public Long isRead(Long memberAlarmId) {
        MemberAlarm findMemberAlarm = findMemberAlarmById(memberAlarmId);
        if(!(findMemberAlarm.isRead())) {
            log.info("알림을 확인하며 상태를 읽음으로 변경");
            findMemberAlarm.changeIsRead();
        }
        return findMemberAlarm.getId();
    }


>>>>>>> 07ac0d9b7f7a038fec1c7ac6d46c228d55a944de
}

