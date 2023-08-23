package ISTP.dtos.help;

import ISTP.domain.help.question.InquiryStatus;
import ISTP.domain.help.question.Question;
import ISTP.domain.help.question.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionSummaryDto {

    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedDate;
    private Long questionTypeId;
    private InquiryStatus inquiryStatus;

    public QuestionSummaryDto(Question question) {
        this.title = question.getTitle();
        this.createdTime = question.getCreateDate();
        this.questionTypeId = question.getQuestionTypeId();
        this.lastModifiedDate = question.getLastModifiedDate();
        this.inquiryStatus = question.getStatus();
    }
}
