package ISTP.domain.help.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"questionType"})
public class QuestionType {

    @Id
    @GeneratedValue
    @Column(name = "question_type_id")
    private Long id;
    @Column(name = "question_type_name")
    private String questionType;

    @JsonIgnore
    @OneToOne(mappedBy = "questionType", fetch = FetchType.LAZY)
    private Question question;


    public QuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
