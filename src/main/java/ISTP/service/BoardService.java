package ISTP.service;

import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
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
        return saveBoard.getId();
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).
                orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void boardTypeSetting(Board board, BoardType boardType) { // 공지사항인지 인터뷰인지 정하기
        board.changeBoardType(boardType);
    }
}
