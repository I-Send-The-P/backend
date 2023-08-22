package ISTP.domain.bloodDonation.accept;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Accept() {
    }

    public Accept(Member member, Request request, AcceptStatus status) {
        this.member = member;
        this.request = request;
        this.status = status;
    }

    public void update_request() {
        this.status = AcceptStatus.수락;
    }

    public void update_cancel() {
        this.status = AcceptStatus.취소;
    }

    public void update_finish() {
        this.status = AcceptStatus.완료;
    }

    //==연관관계 메서드==//
    public void changeAccept(Member member) {
        this.member = member;
    }
}
