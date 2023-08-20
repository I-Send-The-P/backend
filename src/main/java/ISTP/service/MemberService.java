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

    @Transactional //회원 저장 로직
    public Long save(Member member) {

        Member findMember = memberRepository.findById(member.getId()).get();
        if(findMember != null) {
            throw new IllegalArgumentException("중복회원가입 오류 발생");
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

}
