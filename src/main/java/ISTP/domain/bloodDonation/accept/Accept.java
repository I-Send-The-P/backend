package ISTP.domain.bloodDonation.accept;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;


@Entity
public class Accept extends BaseEntity { // 헌혈 해주는 사람

    @Id
    @GeneratedValue
    @Column(name = "accept_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @Enumerated(STRING)
    @Column(name = "accept_status")
    private AcceptStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
