package ISTP.dtos.help;

import ISTP.domain.help.question.InquiryStatus;
import ISTP.domain.help.question.InquiryType;
import ISTP.domain.help.question.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuestionSummaryDto {

    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedDate;
    private InquiryType inquiryType;
    private InquiryStatus inquiryStatus;
    private String nickname;

    public QuestionSummaryDto(Question question) {
        this.title = question.getTitle();
        this.createdTime = question.getCreateDate();
        this.inquiryType = question.getInquiryType();
        this.lastModifiedDate = question.getLastModifiedDate();
        this.inquiryStatus = question.getStatus();
        this.nickname = question.getMember().getNickname();
    }
}
