package ISTP.domain.bloodDonation.request;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

<<<<<<< HEAD
    public Request() {
    }

    public Request(Member member, String sickness, String content, LocalDate duration, String number, String hospital,
                   RequestStatus status, BloodType bloodType, String relationship, String requests_blood_type) {
        this.member = member;
        this.sickness = sickness;
        this.content = content;
        this.duration = duration;
        this.number = number;
        this.hospital = hospital;
        this.status = status;
        this.bloodType = bloodType;
        this.relationship = relationship;
        this.requests_blood_type = requests_blood_type;
    }


    public void update_request() {
        this.status = RequestStatus.신청;
    }

    public void update_status() {
        this.status = RequestStatus.진행;
    }

    public void update_finish() {
        this.status = RequestStatus.완료;
    }

    public void changeRequest(Member member) {
        this.member = member;
    }
}
