package ISTP.domain.bloodDonation.accept;

import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;


@Entity
public class Accept {

    @Id
    @GeneratedValue
    @Column(name = "accept_id")
    private Long id;

    private LocalDateTime acceptDate; // 요청 수락 시간
    private LocalDateTime cancelDate; // 요청 취소 시간

    @Enumerated(STRING)
    @Column(name = "accept_status")
    private AcceptStatus status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;
}
