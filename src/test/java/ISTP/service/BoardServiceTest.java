package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;
    @BeforeEach
    public void before() {

        Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, Gender.MAN, "010-1111-2222", BloodType.A_PLUS, "aaa@naver.com", "인천시");
        Member member2 = new Member("loginId2", "password2", "test2", "별명2", 20, Gender.WOMAN, "010-3333-4444", BloodType.B_PLUS, "bbb@naver.com", "서울시");
        memberService.save(member1);
        memberService.save(member2);
        for(int i = 1; i <= 12; i++) {
            Board board;
            if(i <= 3) {
                board = new Board("title" + i, "content1" + i, BoardType.공지사항, member1);
            }
            else if(i <= 6) {
                board = new Board("title" + i, "content1" + i, BoardType.인터뷰, member1);
            }
            else if(i <= 9) {

                board = new Board("title" + i, "content1" + i, BoardType.공지사항, member2);
            }
            else {
                 board = new Board("title" + i, "content1" + i, BoardType.인터뷰, member2);
            }
            boardService.save(board);
        }
    }

    @Test
    public void findById() {
        Board board = new Board("abc", "abc", BoardType.공지사항, null);
        boardService.save(board);
        Board findBoard = boardService.findById(board.getId());
        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    public void findByIdError() {
        assertThrows(IllegalArgumentException.class, () -> boardService.findById(14L));
    }

    @Test
    public void findAll() {
        List<Board> all = boardService.findAll();
        assertThat(all.size()).isEqualTo(12);
    }

    @Test
    public void findByBoardType() {
        List<Board> boards = boardService.findByBoardType();
        for (Board board : boards) {
            System.out.println("board = " + board);
        }
    }

    @Test
    @Rollback
    public void updateBoard() {
        Board board = new Board("abc", "abc", BoardType.공지사항, null);
        boardService.save(board);
        boardService.updateBoard(board, "updateTitle", "updateContent", BoardType.인터뷰);
        assertThat(board.getTitle()).isEqualTo("updateTitle");
    }

    @Test
    public void deleteBoard() {
        Board board = new Board("abc", "abc", BoardType.공지사항, null);
        boardService.save(board);
        boardService.deleteBoard(board);
        assertThrows(IllegalArgumentException.class, () -> memberService.findById(board.getId()));
    }

}