package ISTP.domain.alarm;

import ISTP.domain.BaseEntity;
import ISTP.domain.MemberAlarm;
import ISTP.domain.board.Board;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "alarm_Id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "request_member_id")
    private Member requestMember;

    private String content;
    private Long requestId;

    @OneToMany(mappedBy = "alarm")
    private List<MemberAlarm> memberAlarms = new ArrayList<>();

    //==연관관계 메서드==//
    public void addMemberAlarms(MemberAlarm memberAlarm) {
        memberAlarms.add(memberAlarm);
    }

    public Alarm(Member requestMember, String content, Long requestId) {
        this.requestMember = requestMember;
        this.content = content;
        this.requestId = requestId;
    }


}
