package ISTP.dtos.help;

import ISTP.domain.help.question.QuestionType;
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
    private String questionType;
}
