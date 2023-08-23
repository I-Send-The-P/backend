package ISTP.domain;

import ISTP.domain.alarm.Alarm;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberAlarm {

    @Id
    @GeneratedValue
    @Column(name = "member_alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accept_member_id")
    private Member acceptMember;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    public MemberAlarm(Member acceptMember, Alarm alarm) {
        this.acceptMember = acceptMember;
        this.alarm = alarm;
    }
}
