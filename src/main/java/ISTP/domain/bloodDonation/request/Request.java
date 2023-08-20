package ISTP.domain.bloodDonation.request;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
public class Request extends BaseEntity { // 헌혈 요청

    @Id
    @GeneratedValue
    @Column(name = "request_id")
    private Long id;

    private String sickness;
    private String content; // 요청 사연
    private LocalDate duration; // 마감 날짜
    private String number; //환자 등록 번호
    private String hospital;
    @Enumerated(STRING)
    private RequestStatus status;
    @Enumerated(STRING)
    private BloodType bloodType;
    private String relationship;
    private String requests_blood_type; // 무슨 헌혈인지


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
