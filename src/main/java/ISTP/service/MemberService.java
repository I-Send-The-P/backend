package ISTP.service;

import ISTP.domain.bloodDonation.BloodTypeCategories;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import ISTP.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final RequestRepository requestRepository;
    private final AcceptRepository acceptRepository;
    private final BoardRepository boardRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final BloodTypeCategoriesRepository bloodTypeCategoriesRepository;

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
    public BloodTypeCategories findByBloodType(String bloodType) {
        BloodTypeCategories byBloodType = bloodTypeCategoriesRepository.findByBloodType(bloodType);
        log.info("혈액형이름으로 블러드타입 찾기 {}", byBloodType);
        return byBloodType;
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
    //휴대폰 중복확인 기능
    public boolean duplicatedPhoneNumber(String phoneNumber) {
        Member findMember = memberRepository.findByPhoneNumber(phoneNumber);
        //이 멤버가 존재하면 이미 전화번호 있는 것
        if(findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 휴대폰 번호입니다");
        }
        log.info("{}는 사용 가능한 휴대번호입니다.", phoneNumber);
        return true;
    }

    public boolean duplicatedAddress(String address) {
        Member findMember = memberRepository.findByAddress(address);
        //이 멤버가 존재하면 이미 전화번호 있는 것
        if(findMember != null) {
            throw new IllegalArgumentException("이미 존재하는 주소입니다");
        }
        log.info("{}는 사용 가능한 주소입니다.", address);
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


    //회원 탈퇴 기능
    /**
     * 탈퇴 시 이 회원과 연관되어있는 내용들 다 없애야하는데 어떻게 할지 고민
     */
    @Transactional
    public void withdrawal(Member member) {
        requestRepository.deleteByMemberId(member.getId());
        acceptRepository.deleteByMemberId(member.getId());

        List<Question> questions = member.getQuestions();
        for (Question question : questions) {
            List<Answer> answers = answerRepository.findV2ByQuestionId(question.getId());
        }
        questionRepository.deleteByMemberId(member.getId());
        answerRepository.deleteByMemberId(member.getId());

        boardRepository.deleteByMemberId(member.getId());
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

    @Transactional
    public ResponseEntity<?> changeMember(Member member, String phoneNumber, String name, String add) {
        boolean nickname = duplicatedNickname(phoneNumber);
        boolean phone = duplicatedPhoneNumber(name);
        boolean address = duplicatedAddress(add);
        if(address && phone && nickname) {
            member.update(phoneNumber, name, add);
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    //알람 ON/OFF로 변경하는 기능
    @Transactional
    public void changeAlarm(Member member) {
        member.changeAlarm();
        if(member.isAlarmStatus()){
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
    public List<Member> findAlarmMember(String bloodType, boolean alarmStatus, String address) {
        log.info("혈액형이 {}이고 알람이 On 이고 주소가 {}인 모든 멤버 조회", bloodType, address);
        BloodTypeCategories findBloodType = bloodTypeCategoriesRepository.findByBloodType(bloodType);
        return memberRepository.findAllByMyBloodTypeIdAndAlarmStatusAndAddress(findBloodType.getId(), alarmStatus, address);
    }

    // 특정 멤버의 요청글 작성 리스트 받아오기
    public List<Request> getMemberBoards(Member member) {
        return member.getRequests();
    }

    // 헌혈랭킹 top 5
    public List<Member> findTop5ByCount() {
       return memberRepository.findTop5ByCount();
    }

    public List<Member> findAllByBloodTypeIdAndAlarmStatus(Long bloodTypeId) {
        return memberRepository.findAllByBloodTypeIdAndAlarmStatus(bloodTypeId);
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findOneByLoginId(loginId);
    }

}
