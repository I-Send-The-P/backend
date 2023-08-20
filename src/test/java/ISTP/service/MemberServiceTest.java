package ISTP.service;

import ISTP.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void duplicatedLoginId() {
        Member member1 = new Member("loginId1", "password1");
        memberService.save(member1);

        String loginId = "loginId2";
        assertThat(memberService.duplicatedLoginId(loginId)).isTrue();
    }

    @Test
    public void duplicatedLoginIdError() {
        Member member1 = new Member("loginId1", "password1");
        memberService.save(member1);

        String loginId = "loginId1";
        assertThrows(IllegalArgumentException.class, () -> memberService.duplicatedLoginId(loginId));
    }

    @Test
    public void duplicatedNickname() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        String nickname = "nickname2";
        assertThat(memberService.duplicatedNickname(nickname)).isTrue();
    }

    @Test
    public void duplicatedNicknameError() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        String nickname = "nickname1";
        assertThrows(IllegalArgumentException.class, () -> memberService.duplicatedNickname(nickname));
    }

    @Test
    public void passwordReEnter() {
        String password = "aaa";
        String rePassword = "aaa";
        boolean result = memberService.passwordReEnter(password, rePassword);
        assertThat(result).isTrue();
    }
    @Test
    public void passwordReEnterError() {
        String password = "aaa";
        String rePassword = "bbb";
        assertThrows(IllegalArgumentException.class, () -> memberService.passwordReEnter(password, rePassword));
    }
}