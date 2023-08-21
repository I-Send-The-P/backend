package ISTP;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;


    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitMemberService {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, Gender.MAN, "010-1111-2222", BloodType.A_PLUS, "aaa@naver.com", "인천시");
            Member member2 = new Member("loginId2", "password2", "test2", "별명2", 20, Gender.WOMAN, "010-3333-4444", BloodType.B_PLUS, "bbb@naver.com", "서울시");
            em.persist(member1);
            em.persist(member2);
        }
    }
}
