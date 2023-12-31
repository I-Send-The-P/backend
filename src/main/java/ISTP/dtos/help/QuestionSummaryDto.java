package ISTP.dtos.help;

import ISTP.domain.help.question.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionSummaryDto {

    private String title;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedDate;
    private Long questionTypeId;
    private boolean answerStatus;
    private Long id;

    public QuestionSummaryDto(Question question) {
        this.title = question.getTitle();
        this.content = question.getContent();
        this.createdTime = question.getCreateDate();
        this.questionTypeId = question.getQuestionTypeId();
        this.lastModifiedDate = question.getLastModifiedDate();
        this.answerStatus = question.isAnswerStatus();
        this.id = question.getId();
    }
}
