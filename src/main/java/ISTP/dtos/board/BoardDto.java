package ISTP.dtos.board;


import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDto {

    private String title;
    private String content;
    private BoardType boardType;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    public BoardDto(Board board) {
        title = board.getTitle();
        content = board.getContent();
        boardType = board.getBoardType();
        createDate = board.getCreateDate();
        lastModifiedDate = board.getLastModifiedDate();
    }
}