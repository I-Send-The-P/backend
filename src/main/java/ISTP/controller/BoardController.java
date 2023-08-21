package ISTP.controller;


import ISTP.domain.board.Board;
import ISTP.dtos.board.BoardSummaryDTO;
import ISTP.service.BoardService;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping
    public List<BoardSummaryDTO> boardList() {
        //공지사항이 제일 위에 나타나도록 조회
        List<Board> boards = boardService.findByBoardType();
        List<BoardSummaryDTO> boardSummaryDTOS = new ArrayList<>();
        for (Board board : boards) {
            BoardSummaryDTO boardSummaryDTO = new BoardSummaryDTO(board);
            boardSummaryDTOS.add(boardSummaryDTO);
        }
        return boardSummaryDTOS;
    }


}
