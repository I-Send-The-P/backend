package ISTP.repository;

import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.boardType = :boardType ORDER BY b.createDate DESC")
    List<Board> findAllByBoardType(@Param("boardType") BoardType boardType);

    void deleteByMemberId(Long memberId);


}
