package ISTP.dtos.help;

import ISTP.domain.help.Answer;
import ISTP.domain.help.question.InquiryStatus;
import ISTP.domain.help.question.Question;
import ISTP.domain.help.question.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HelpDto {

    private String title;
    private String questionContent;
    private LocalDateTime questionCreatedTime;
    private LocalDateTime questionLastModifiedDate;
    private QuestionType questionType;
    private InquiryStatus inquiryStatus;
    private LocalDateTime answerCreatedTime;
    private LocalDateTime answerLastModifiedDate;
    private String answerContent;

    public HelpDto(Question question, Answer answer) {
        this.title = question.getTitle();
        this.questionContent = question.getContent();
        this.questionCreatedTime = question.getCreateDate();
        this.questionLastModifiedDate = question.getLastModifiedDate();
        this.questionType = question.getQuestionType();
        this.inquiryStatus = question.getStatus();
        this.answerContent = answer.getContent();
        this.answerCreatedTime = answer.getCreateDate();
        this.answerLastModifiedDate = answer.getLastModifiedDate();
    }
}
