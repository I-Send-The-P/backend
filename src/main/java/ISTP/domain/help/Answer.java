package ISTP.domain.help;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Answer extends BaseEntity { // 문의답변

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}
