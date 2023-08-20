package ISTP.domain.bloodDonation.accept;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
public class Accept extends BaseEntity { // 헌혈 해주는 사람

    @Id
    @GeneratedValue
    @Column(name = "accept_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @Enumerated(STRING)
    @Column(name = "accept_status")
    private AcceptStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //==연관관계 메서드==//
    public void changeAccept(Member member) {
        this.member = member;
    }
}
