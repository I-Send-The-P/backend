package ISTP.domain.help;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(of = {"id", "content"})
@NoArgsConstructor
public class Answer extends BaseEntity { // 문의답변

    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(String content, Member member, Question question) {
        this.content = content;
        if(member != null) {
            changeAnswer(member);
        }
        this.question = question;
    }



    //==연관관계 메서드==//
    public void changeAnswer(Member member) {
        this.member = member;
        member.addAnswer(this); //연관관계 설정
    }

}
