package ISTP.service;

import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import ISTP.domain.member.Member;
import ISTP.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    @Transactional
    public Long save(Board board) {
        Board saveBoard = boardRepository.save(board);
        log.info("{} 게시글 생성", board.getTitle());
        return saveBoard.getId();
    }


    public Board findById(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시입니다"));
        log.info("아이디로 게시글 찾기 {}", findBoard);
        return findBoard;
    }

    public List<Board> findAll() {
        log.info("모든 게시글 조회");
        return boardRepository.findAll();
    }
    public List<Board> findByBoardType() {
        log.info("공지사항이 맨 위에 나오도록 모든 게시글 조회");
        List<Board> noticeBoards = boardRepository.findAllByBoardType(BoardType.공지사항);
        List<Board> interviewBoards = boardRepository.findAllByBoardType(BoardType.인터뷰);

        noticeBoards.addAll(interviewBoards); // 공지사항 리스트에 인터뷰 리스트를 추가
        return noticeBoards;
    }

    @Transactional
    public void updateBoard(Board board, String updateTitle, String updateContent, BoardType updateBoardType) {
        board.updateBoard(updateTitle, updateContent, updateBoardType);
        log.info("게시글 업데이트 완료 {}", board);
    }

    @Transactional
    public void deleteBoard(Board board) {
        log.info("{} 게시글 삭제", board.getId());
        boardRepository.delete(board);

    }

}
