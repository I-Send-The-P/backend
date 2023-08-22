package ISTP.controller;


import ISTP.domain.board.Board;
import ISTP.domain.member.Member;
import ISTP.dtos.board.BoardDto;
import ISTP.dtos.board.BoardEditForm;
import ISTP.dtos.board.BoardSaveForm;
import ISTP.dtos.board.BoardSummaryDto;
import ISTP.service.BoardService;
import ISTP.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    //공지사항 리스트 조회
    @ResponseBody
    @GetMapping("/list")
    public List<BoardSummaryDto> boardList() {
        //공지사항이 제일 위에 나타나도록 조회
        List<Board> boards = boardService.findByBoardType();
        List<BoardSummaryDto> boardSummaryDTOS = new ArrayList<>();
        for (Board board : boards) {
            BoardSummaryDto boardSummaryDTO = new BoardSummaryDto(board);
            boardSummaryDTOS.add(boardSummaryDTO);
        }
        return boardSummaryDTOS;
    }

    /**
     * 로그인 세션에서 회원 정보 가져오는 기능 추가 구현해야할듯~~ 우선은 그냥 파라미터로 멤버아이디 하나 임의로 받겠습니다
     */
    @PostMapping("create")
    public Long save(@Validated @RequestBody BoardSaveForm form, BindingResult bindingResult, @RequestParam Long memberId) {

        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //에러처리 어케 할까여
            throw new IllegalArgumentException("게시글 작성 시 오류 발생");
        }
        Member member = memberService.findById(memberId);
        Board board = new Board(form.getTitle(), form.getContent(), form.getBoardType(), member);
        boardService.save(board);
        return board.getId();
    }

    //각 게시글 조회
    @ResponseBody
    @GetMapping("{boardId}")
    public BoardDto board(@PathVariable Long boardId) {
        Board board = boardService.findById(boardId);
        BoardDto boardDto = new BoardDto(board);
        return boardDto;
    }

    //게시글 수정
    @PostMapping("{boardId}/edit")
    public Long editBoard(@Validated @RequestBody BoardEditForm form, BindingResult bindingResult, @PathVariable Long boardId) {

        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //에러처리 어케 할까여
            throw new IllegalArgumentException("게시글 작성 시 오류 발생");
        }
        Board board = boardService.findById(boardId);
        boardService.updateBoard(board, form.getTitle(), form.getContent(), form.getBoardType());
        return board.getId();
    }

    //게시글 삭제
    @DeleteMapping("{boardId}/delete")
    public void deleteBoard(@PathVariable Long boardId) {
        Board board = boardService.findById(boardId);
        boardService.deleteBoard(board);
    }


}
