package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Alarm;
import ISTP.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @BeforeEach
    public void before() {

        for(int i = 1; i <= 20; i++) {
            Member member = new Member("id" + i, "pass" + i, "nick" + i, "address" + i);
            if(i <= 5) {
                member.setMyBloodType(BloodType.A_PLUS);
            }
            else if(i <= 10) {
                member.setMyBloodType(BloodType.B_PLUS);
            }
            else if(i <= 15) {
                member.setMyBloodType(BloodType.O_PLUS);
            }
            else {
                member.setMyBloodType(BloodType.AB_PLUS);;
            }
            memberService.save(member);
        }
    }

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
    public void findAll() {
        Member member1 = new Member("loginId1", "password1");
        Member member2 = new Member("loginId2", "password2");

        memberService.save(member1);
        memberService.save(member2);

        List<Member> allMember = memberService.findAll();
        assertThat(allMember.size()).isEqualTo(22);
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
        boolean result = memberService.passwordReEnter(password, rePassword);
        assertThat(result).isFalse();
    }

    @Test
    public void login() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        Member loginMember = memberService.login("loginId1", "password1");
        assertThat(loginMember).isEqualTo(member1);
    }

    @Test
    public void loginError() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        Member loginMember = memberService.login("loginId1", "password2");
        assertThat(loginMember).isNull();
    }

    @Test
    public void withdrawal() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        memberService.withdrawal(member1);
        List<Member> all = memberService.findAll();
        assertThat(all).doesNotContain(member1);
    }

    @Test
    public void changeNickname() {
        Member member1 = new Member("loginId1", "password1", "nickname1");
        memberService.save(member1);

        memberService.changeNickname(member1, "changeNick");
        assertThat(member1.getNickname()).isEqualTo("changeNick");
    }

    @Test
    public void changeAddress() {
        Member member1 = new Member("loginId1", "password1", "nickname1", "인천시");
        memberService.save(member1);
        memberService.changeAddress(member1, "서울시");
        assertThat(member1.getAddress()).isEqualTo("서울시");
    }

    @Test
    public void changeAlarm() {
        Member member1 = new Member("loginId1", "password1", "nickname1", "인천시");
        memberService.save(member1);
        assertThat(member1.isAlarm()).isEqualTo(true);
        memberService.changeAlarm(member1);
        assertThat(member1.isAlarm()).isEqualTo(false);
        memberService.changeAlarm(member1);
        assertThat(member1.isAlarm()).isEqualTo(true);
    }

    @Test
    public void countPlus() {
        Member member1 = new Member("loginId1", "password1", "nickname1", "인천시");
        memberService.save(member1);
        assertThat(member1.getCount()).isEqualTo(0);
        memberService.countPlus(member1);
        assertThat(member1.getCount()).isEqualTo(1);
        memberService.countPlus(member1);
        assertThat(member1.getCount()).isEqualTo(2);
    }

    @Test
    public void findAllByMyBloodType() {
        List<Member> allByMyBloodType = memberService.findAllByMyBloodType(BloodType.A_PLUS);

        assertThat(allByMyBloodType.size()).isEqualTo(5);
        for (Member member : allByMyBloodType) {
            assertThat(member.getMyBloodType()).isEqualTo(BloodType.A_PLUS);
        }
    }


    @Test
    public void findTop5ByDonationCount() {
        int count = 10;
        List<Member> all = memberService.findAll();
        for (Member member : all) {
            for(int i = 0; i <= count; i++) {
                member.countPlus();
            }
        }
        List<Member> top5ByDonationCount = memberService.findTop5ByCount();
        assertThat(top5ByDonationCount.size()).isEqualTo(5);
    }
}