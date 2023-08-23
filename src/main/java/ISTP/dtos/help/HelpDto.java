package ISTP.dtos.help;

import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
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
    private Long questionTypeId;
    private boolean answerStatus;
    private LocalDateTime answerCreatedTime;
    private LocalDateTime answerLastModifiedDate;
    private String answerContent;

    public HelpDto(Question question, Answer answer) {
        this.title = question.getTitle();
        this.questionContent = question.getContent();
        this.questionCreatedTime = question.getCreateDate();
        this.questionLastModifiedDate = question.getLastModifiedDate();
        this.questionTypeId = question.getQuestionTypeId();
        this.answerStatus = question.isAnswerStatus();
        this.answerContent = answer.getContent();
        this.answerCreatedTime = answer.getCreateDate();
        this.answerLastModifiedDate = answer.getLastModifiedDate();
    }
}
