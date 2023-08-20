package ISTP.domain.help;

import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String content;
    private LocalDateTime createDate; // 작성 시간

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
