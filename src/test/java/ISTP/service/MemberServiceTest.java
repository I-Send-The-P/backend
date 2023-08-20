package ISTP.service;

import ISTP.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    //회원 저장 테스트
    @Test
    @Transactional
    public void save() {
        Member member = new Member("loginId1", "password1");
        Long memberId = memberService.save(member);

        Member findMember = memberService.findById(memberId);
        assertThat(member).isEqualTo(findMember);
    }

}