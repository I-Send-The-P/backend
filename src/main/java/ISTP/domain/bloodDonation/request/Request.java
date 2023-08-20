package ISTP.domain.bloodDonation.request;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
public class Request {

    @Id
    @GeneratedValue
    @Column(name = "request_id")
    private Long id;

    private String content;

    private String number; //환자 등록 번호
    @Enumerated(STRING)
    private Hospital hospital;
    @Enumerated(STRING)
    private RequestStatus status;
    @Enumerated(STRING)
    private BloodType bloodType;

    private LocalDateTime CreateDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
