package ISTP.service;

import ISTP.domain.board.Board;
import ISTP.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        log.info("아이디로 게시글 찾기 {}", findBoard);
        return findBoard;
    }

    public Board findByIdWithMember(Long boardId) {
        Board findBoard = boardRepository.findByIdWithMember(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        log.info("아이디로 게시글 찾기 {}", findBoard);
        return findBoard;
    }

    public List<Board> findAll() {
        log.info("모든 게시글 조회");
        return boardRepository.findAll();
    }

    public List<Board> findByBoardType() {
        log.info("공지사항이 맨 위에 나오도록 모든 게시글 조회");
        List<Board> noticeBoards = boardRepository.findAllByBoardType(true);
        List<Board> interviewBoards = boardRepository.findAllByBoardType(false);

        noticeBoards.addAll(interviewBoards); // 공지사항 리스트에 인터뷰 리스트를 추가
        return noticeBoards;
    }

    @Transactional
    public void updateBoard(Board board, String updateTitle, String updateContent, Boolean updateIsNotice) {
        board.updateBoard(updateTitle, updateContent, updateIsNotice);
        log.info("게시글 업데이트 완료 {}", board);
    }

    @Transactional
    public void deleteBoard(Board board) {
        log.info("{} 게시글 삭제", board.getId());
        boardRepository.delete(board);
    }

}
