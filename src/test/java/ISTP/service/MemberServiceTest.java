package ISTP.service;

import ISTP.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void duplicatedSave() {
        // Given
        Member member1 = new Member("loginId1", "password1");
        Member member2 = new Member("loginId1", "password2");

        // When
        memberService.save(member1);

        // Then
        assertThrows(IllegalArgumentException.class, () -> memberService.save(member2));
    }

    @Test
    public void findPassword() {
        Member member1 = new Member("loginId1", "password1");
        memberService.save(member1);
        String findPassword = memberService.findByPassword(member1.getLoginId());
        assertThat(member1.getPassword()).isEqualTo(findPassword);
    }

    @Test
    public void findNotPassword() {
        Member member1 = new Member("loginId1", "password1");
        memberService.save(member1);
        assertThrows(IllegalArgumentException.class, () -> memberService.findByPassword("notMember"));
    }

}