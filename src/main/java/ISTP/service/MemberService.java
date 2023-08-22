package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Alarm;
import ISTP.domain.member.Member;
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
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member) {
        Member existingMember = memberRepository.findByLoginId(member.getLoginId());
        if (existingMember != null) {
            throw new IllegalArgumentException("이미 가입된 회원입니다");
        }

        memberRepository.save(member);
        log.info("회원 가입 성공 {}", member);
        return member.getId();
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }


    public Member findById(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        log.info("아이디로 회원 찾기 {}", findMember);
        return findMember;
    }

    //비밀번호 까먹었을 때 아이디로 비밀번호 찾는 로직
    public String findByPassword(String loginId) {
        Member findMember = memberRepository.findByLoginId(loginId);

        if(findMember == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다");
        }
        log.info("아이디로 비밀번호 찾기");
        return findMember.getPassword();
    }


    //아이디 중복확인 기능
    public boolean duplicatedLoginId(String loginId) {
        Member findMember = memberRepository.findByLoginId(loginId);
        //이 멤버가 존재하면 이미 아이디 있는 것
        if(findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }
        log.info("{}는 사용 가능한 아이디입니다.", loginId);
        return true;
    }

    //닉네임 중복확인 기능
    public boolean duplicatedNickname(String nickname) {
        Member findMember = memberRepository.findByNickname(nickname);
        //이 멤버가 존재하면 이미 닉네임 있는 것
        if(findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다");
        }
        log.info("{}는 사용 가능한 닉네임입니다.", nickname);
        return true;
    }

    //비밀번호 재입력 확인 기능
    public boolean passwordReEnter(String password, String rePassword) {
        if(!(password.equals(rePassword))) {
            log.info("비밀번호가 일치하지 않습니다 {} != {}", password, rePassword);
            return false;
        }
        log.info("비밀번호가 일치합니다 {}", password);
        return true;
    }

    /**
     * 로그인 처리 기능
     * null이면 로그인 실패
     */
    public Member login(String loginId, String password) {
        return memberRepository.findLoginByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }


    //회원 탈퇴 기능
    /**
     * 탈퇴 시 이 회원과 연관되어있는 내용들 다 없애야하는데 어떻게 할지 고민 ㅠ
     */
    @Transactional
    public void withdrawal(Member member) {
        memberRepository.delete(member);
        log.info("{} 회원 삭제 완료", member);
    }

    //닉네임 변경하는 기능
    @Transactional
    public void changeNickname(Member member, String changeNickname) {
        if(duplicatedNickname(changeNickname)) {
            member.changeNickname(changeNickname);
            log.info("{}로 닉네임 변경 완료", changeNickname);
        }
    }

    //주소 변경하는 기능
    @Transactional
    public void changeAddress(Member member, String changeAddress) {
            member.changeAddress(changeAddress);
            log.info("{}로 주소 변경 완료", changeAddress);
    }


    //알람 ON/OFF로 변경하는 기능
    @Transactional
    public void changeAlarm(Member member) {
        member.changeAlarm();
        if(member.getAlarm().equals(Alarm.가능)){
            log.info("알람 OFF로 변경");
        }
        else {
            log.info("알람 ON으로 변경");
        }
    }

    @Transactional
    public void countPlus(Member member) {
        member.countPlus();
        log.info("{}의 헌혈 횟수 {}로 증가", member.getNickname(), member.getCount());
    }


    //알람 발송을 위해 혈액형이 같은 모든 멤버 조회 메서드
    public List<Member> findAllByMyBloodType(BloodType bloodType) {
        log.info("혈액형이 {}인 모든 멤버 조회", bloodType);
        return memberRepository.findAllByMyBloodType(bloodType);
    }

    // 특정 멤버의 요청글 작성 리스트 받아오기
    public List<Request> getMemberBoards(Member member) {
        return member.getRequests();
    }

    // 헌혈랭킹 top 5
    public List<Member> findTop5ByCount() {
       return memberRepository.findTop5ByCount();
    }

}
