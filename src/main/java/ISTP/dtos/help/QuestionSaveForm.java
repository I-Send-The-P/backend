package ISTP.dtos.help;

import ISTP.domain.board.BoardType;
import ISTP.domain.help.question.InquiryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSaveForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private InquiryType inquiryType;
}
