package ISTP.domain.help;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer extends BaseEntity { // 문의답변

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
