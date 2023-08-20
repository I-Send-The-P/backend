package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestServiceTest {

    @Autowired
    RequestService requestService;

    @Test
    public void saveRequestTest() {
        Member member = new Member("abc", "aaa");

        Request request = new Request(member, "질병", "내용", LocalDate.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, BloodType.A_PLUS,
                "가족", "혈소판 헌혈");

        Long savedRequest = requestService.save(request);
        Request findId = requestService.findById(request.getId());
        assertThat(savedRequest).isEqualTo(findId.getId());
    }

    @Test
    public void update_status() {
        Member member = new Member("abc", "aaa");

        Request request = new Request(member, "질병", "내용", LocalDate.now().plusDays(3),
                "1111-2222", "병원", RequestStatus.신청, BloodType.A_PLUS,
                "가족", "혈소판 헌혈");

        requestService.changeStatus(request);

        assertThat(request.getStatus()).isEqualTo(RequestStatus.진행);
    }
}