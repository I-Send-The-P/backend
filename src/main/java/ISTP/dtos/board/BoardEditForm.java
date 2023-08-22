package ISTP.Dtos.board;

import ISTP.domain.board.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardEditForm {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private BoardType boardType;
}
