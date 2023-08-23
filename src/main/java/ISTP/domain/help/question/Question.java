package ISTP.domain.help.question;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.Answer;
import ISTP.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@ToString(of = {"id", "title", "content", "questionType", "status"})
@NoArgsConstructor
public class Question extends BaseEntity { // 문의사항

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;
    private String content;

    @Column(name = "answer_status")
    private boolean answerStatus; // 답변 상태 false면 답변 못받은 상태, true이면 답변 받은 상태

    @Column(name = "question_type_id")
    private Long questionTypeId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Question(String title, String content, QuestionType questionType, Member member) {
        this.title = title;
        this.content = content;
        this.answerStatus = false;
        this.questionTypeId = questionType.getId();
        if(member != null) {
            changeQuestion(member);
        }
    }

    //==연관관계 메서드==//
    public void changeQuestion(Member member) {
        this.member = member;
        member.addQuestion(this); //연관 관계설정
    }

    //답변을 받으면 문의중 -> 문의 상태로 바꾸는 메서드
    public void changeStatus() {
        this.answerStatus = true;
    }


    //문의글 수정하여 업데이트하는 메서드
    public void updateQuestion(String title, String content, QuestionType questionType) {
        this.title = title;
        this.content = content;
        this.questionTypeId = questionType.getId();
    }
}
