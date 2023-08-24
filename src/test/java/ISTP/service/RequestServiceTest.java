package ISTP.service;

import ISTP.domain.bloodDonation.BloodTypeCategories;
import ISTP.domain.bloodDonation.BloodTypeName;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static ISTP.domain.bloodDonation.BloodTypeName.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RequestServiceTest {

    @Autowired
    RequestService requestService;

    @Autowired
    MemberService memberService;

    @Test
    public void saveRequestTest() {
        Member member = new Member("abc", "aaa");

        memberService.save(member);
        BloodTypeCategories bloodType = memberService.findByBloodType(A_PLUS);
        Request request = new Request(member, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");

        Long savedRequest = requestService.save(request);
        Request findId = requestService.findById(request.getId());
        assertThat(savedRequest).isEqualTo(findId.getId());
    }

    @Test
    public void update_status() {
        Member member = new Member("abc", "aaa");

        BloodTypeCategories bloodType = memberService.findByBloodType(A_PLUS);
        Request request = new Request(member, "질병", "제목", "내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");

        requestService.changeStatus(request);

        assertThat(request.getStatus()).isEqualTo(RequestStatus.진행);
    }

    @Test
    public void delete_request() {
        Member member = new Member("abc", "aaa");

        BloodTypeCategories bloodType = memberService.findByBloodType(A_PLUS);
        Request request = new Request(member, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");

        requestService.save(request);

        requestService.delete(request.getId());

        assertThat(requestService.findAll()).isNotIn(request);
    }

    @Test
    @Rollback(false)
    public void region_request_test() { // 같은 지역만 조회
        BloodTypeCategories bloodType = memberService.findByBloodType(A_PLUS);
        Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, true, "010-1111-2222", bloodType, "aaa@naver.com", "서울시", true);
        Member member2 = new Member("loginId2", "password2", "test2", "별명2", 10, true, "010-1111-2222", bloodType, "aaav@naver.com", "인천시", false);
        Member member3 = new Member("loginId3", "password3", "test3", "별명3", 10, true, "010-1111-2222", bloodType, "aaacd@naver.com", "인천시", true);
        Member member4 = new Member("loginId4", "password4", "test4", "별명4", 10, true, "010-1111-2222", bloodType, "aaad@naver.com", "인천시", true);
        Member member5 = new Member("loginId5", "password5", "test5", "별명5", 10, true, "010-1111-2222", bloodType, "aaasdfad@naver.com", "인천시", true);


        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);
        memberService.save(member4);
        memberService.save(member5);


        Request request1 = new Request(member1, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");
        Request request2 = new Request(member2, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");
        Request request3 = new Request(member3, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, bloodType,
                "가족", "혈소판 헌혈", "인천");

        requestService.save(request1);
        requestService.save(request2);
        requestService.save(request3);

        List<Member> regionByMemberBloodType = requestService.findRegionByMemberBloodType(request3.getAddress(), request3.getRequests_blood_type());

        for (Member member : regionByMemberBloodType) {
            System.out.println("member = " + member.getLoginId() + member.getAddress());
        }

    }

    @Test
    @Rollback(false)
    public void alarm_request_test() {
        BloodTypeCategories byBloodTypeA = memberService.findByBloodType(A_PLUS);
        BloodTypeCategories byBloodTypeB = memberService.findByBloodType(B_PLUS);
        Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, true, "010-1111-2222", byBloodTypeA, "aaa@naver.com", "서울시", true);
        Member member2 = new Member("loginId2", "password2", "test2", "별명2", 10, true, "010-1111-2222", byBloodTypeA, "aaav@naver.com", "인천시", false);
        Member member3 = new Member("loginId3", "password3", "test3", "별명3", 10, true, "010-1111-2222", byBloodTypeB, "aaacd@naver.com", "인천시", true);
        Member member4 = new Member("loginId4", "password4", "test4", "별명4", 10, true, "010-1111-2222", byBloodTypeA, "aaad@naver.com", "인천시", true);
        Member member5 = new Member("loginId5", "password5", "test5", "별명5", 10, true, "010-1111-2222", byBloodTypeA, "aaasdfad@naver.com", "인천시", true);


        memberService.save(member1);
        memberService.save(member2);
        memberService.save(member3);
        memberService.save(member4);
        memberService.save(member5);


        Request request1 = new Request(member1, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, byBloodTypeA,
                "가족", "혈소판 헌혈", "인천");
        Request request2 = new Request(member2, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, byBloodTypeA,
                "가족", "혈소판 헌혈", "인천");
        Request request3 = new Request(member3, "질병", "제목","내용", LocalDateTime.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, byBloodTypeA,
                "가족", "혈소판 헌혈", "인천");

        requestService.save(request1);
        requestService.save(request2);
        requestService.save(request3);

        List<Member> regionByMemberBloodType = requestService.findAllByMemberBloodType(request3.getRequests_blood_type());

        for (Member member : regionByMemberBloodType) {
            System.out.println("member = " + member.getLoginId());
        }
    }
}