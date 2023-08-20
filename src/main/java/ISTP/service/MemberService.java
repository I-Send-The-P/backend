package ISTP.service;

import ISTP.domain.member.Member;
import ISTP.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
