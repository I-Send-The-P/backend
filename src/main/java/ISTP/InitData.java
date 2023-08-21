package ISTP;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initMemberService;


    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitService {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, Gender.MAN, "010-1111-2222", BloodType.A_PLUS, "aaa@naver.com", "인천시");
            Member member2 = new Member("loginId2", "password2", "test2", "별명2", 20, Gender.WOMAN, "010-3333-4444", BloodType.B_PLUS, "bbb@naver.com", "서울시");
            em.persist(member1);
            em.persist(member2);

            Request request1 = new Request(member1, "sickness1", "title1", "content1", LocalDate.now().plusDays(1), "111-111", "나사렛병원", RequestStatus.신청, BloodType.A_PLUS, "부", "혈소판1");
            Request request2 = new Request(member1, "sickness2", "title2", "content2", LocalDate.now().plusDays(2), "222-222", "나사렛병원", RequestStatus.신청, BloodType.B_PLUS, "모", "혈소판2");
            Request request3 = new Request(member1, "sickness3", "title3", "content3", LocalDate.now().plusDays(3), "333-333", "나사렛병원", RequestStatus.신청, BloodType.A_PLUS, "친구", "혈소판3");
            Request request4 = new Request(member2, "sickness4", "title4", "content4", LocalDate.now().plusDays(4), "444-444", "인하대병원", RequestStatus.신청, BloodType.A_PLUS, "지인", "혈소판4");
            Request request5 = new Request(member2, "sickness5", "title5", "content5", LocalDate.now().plusDays(5), "555-555", "인하대병원", RequestStatus.신청, BloodType.A_PLUS, "동생", "혈소판5");
            Request request6 = new Request(member2, "sickness6", "title6", "content6", LocalDate.now().plusDays(6), "666-666", "인하대병원", RequestStatus.신청, BloodType.B_PLUS, "형", "혈소판6");

            member1.addRequest(request1);
            member1.addRequest(request2);
            member1.addRequest(request3);
            member2.addRequest(request4);
            member2.addRequest(request5);
            member2.addRequest(request6);

            em.persist(request1);
            em.persist(request2);
            em.persist(request3);
            em.persist(request4);
            em.persist(request5);
            em.persist(request6);

            for(int i = 1; i <= 12; i++) {
                Board board;
                if(i <= 3) {
                    board = new Board("title" + i, "content1" + i, BoardType.공지사항, member1);
                }
                else if(i <= 6) {
                    board = new Board("title" + i, "content1" + i, BoardType.인터뷰, member1);
                }
                else if(i <= 9) {

                    board = new Board("title" + i, "content1" + i, BoardType.공지사항, member2);
                }
                else {
                    board = new Board("title" + i, "content1" + i, BoardType.인터뷰, member2);
                }
                em.persist(board);
            }
        }
        }

}
