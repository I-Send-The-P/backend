package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import ISTP.domain.help.question.InquiryStatus;
import ISTP.domain.help.question.InquiryType;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    MemberService memberService;

    @BeforeEach
    public void before() {
        Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, Gender.MAN, "010-1111-2222", BloodType.A_PLUS, "aaa@naver.com", "인천시");
        Member member2 = new Member("loginId2", "password2", "test2", "별명2", 20, Gender.WOMAN, "010-3333-4444", BloodType.B_PLUS, "bbb@naver.com", "서울시");
        memberService.save(member1);
        memberService.save(member2);
        for(int i = 1; i <= 12; i++) {
            Question question;
            if(i <= 3) {
                question = new Question("title" + i, "content" + i, InquiryType.계정문의, member1);
            }
            else if(i <= 6) {
                question = new Question("title" + i, "content" + i, InquiryType.건의사항, member2);
            }
            else if(i <= 9) {
                question = new Question("title" + i, "content" + i, InquiryType.프로그램문의, member1);
            }
            else {
                question = new Question("title" + i, "content" + i, InquiryType.기타, member2);
            }
            questionService.save(question);
        }
    }

    @Test
    public void findById() {
        Question question = new Question("abc", "abc", InquiryType.계정문의, null);
        questionService.save(question);
        Question findQuestion = questionService.findById(question.getId());
        assertThat(findQuestion).isEqualTo(question);
    }

    @Test
    public void findByIdError() {
        assertThrows(IllegalArgumentException.class, () -> questionService.findById(14L));
    }

    @Test
    public void findAll() {
        List<Question> all = questionService.findAll();
        assertThat(all.size()).isEqualTo(12);
    }

    @Test
    public void changeStatus() {
        Question question = new Question("abc", "abc", InquiryType.계정문의, null);
        questionService.save(question);
        questionService.changeStatus(question);
        assertThat(question.getStatus()).isEqualTo(InquiryStatus.문의완료);
    }
    @Test
    public void updateQuestion() {
        Question question = new Question("abc", "abc", InquiryType.계정문의, null);
        questionService.save(question);
        questionService.updateQuestion(question, "updateTitle", "updateContent", InquiryType.프로그램문의);
        assertThat(question.getTitle()).isEqualTo("updateTitle");
        assertThat(question.getContent()).isEqualTo("updateContent");
        assertThat(question.getInquiryType()).isEqualTo(InquiryType.프로그램문의);
    }

    @Test
    public void deleteQuestion() {
        Question question = new Question("abc", "abc", InquiryType.계정문의, null);
        questionService.save(question);
        questionService.deleteQuestion(question);

        assertThrows(IllegalArgumentException.class, () -> questionService.findById(question.getId()));
    }

}