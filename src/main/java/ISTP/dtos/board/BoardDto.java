package ISTP.Dtos.board;


import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import lombok.Data;
import lombok.NoArgsConstructor;

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