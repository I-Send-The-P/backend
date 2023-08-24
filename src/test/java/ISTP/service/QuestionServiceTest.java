package ISTP.service;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.help.question.Question;
import ISTP.domain.help.question.QuestionType;
import ISTP.domain.help.question.QuestionTypeName;
import ISTP.domain.member.Gender;
import ISTP.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static ISTP.domain.help.question.QuestionTypeName.*;
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
        Member member1 = new Member("loginId1", "password1", "test1", "별명1", 10, Gender.MAN, "010-1111-2222", BloodType.A_PLUS, "aaa@naver.com", "인천시", true);
        Member member2 = new Member("loginId2", "password2", "test2", "별명2", 20, Gender.WOMAN, "010-3333-4444", BloodType.B_PLUS, "bbb@naver.com", "서울시", true);
        memberService.save(member1);
        memberService.save(member2);
        QuestionType questionType1 = new QuestionType(ACCOUNT);
        questionService.saveV2(questionType1);
        QuestionType questionType2 = new QuestionType(PROGRAM);
        questionService.saveV2(questionType2);
        QuestionType questionType3 = new QuestionType(SUGGESTION);
        questionService.saveV2(questionType3);
        QuestionType questionType4 = new QuestionType(ETC);
        questionService.saveV2(questionType4);
        for(int i = 1; i <= 12; i++) {
            Question question;
            if(i <= 3) {
                QuestionType questionType = questionService.findByQuestionType(ACCOUNT);
                question = new Question("title" + i, "content" + i, questionType, member1);
            }
            else if(i <= 6) {
                QuestionType questionType = questionService.findByQuestionType(SUGGESTION);
                question = new Question("title" + i, "content" + i, questionType, member2);
            }
            else if(i <= 9) {
                QuestionType questionType = questionService.findByQuestionType(PROGRAM);
                question = new Question("title" + i, "content" + i, questionType, member1);
            }
            else {
                QuestionType questionType = questionService.findByQuestionType(ETC);
                question = new Question("title" + i, "content" + i, questionType, member2);
            }
            questionService.save(question);
        }
    }

    @Test
    public void findById() {
        QuestionType questionType = questionService.findByQuestionType(ACCOUNT);
        Question question = new Question("abc", "abc", questionType, null);
        questionService.save(question);
        Question findQuestion = questionService.findById(question.getId());
        assertThat(findQuestion).isEqualTo(question);
    }

    @Test
    public void findByIdError() {
        assertThrows(IllegalArgumentException.class, () -> questionService.findById(14L));
    }

    @Test
    public void updateQuestion() {
        QuestionType questionType = questionService.findByQuestionType(ACCOUNT);
        Question question = new Question("abc", "abc", questionType, null);
        questionService.save(question);
        QuestionType updateQUestionType = questionService.findByQuestionType(PROGRAM);
        questionService.updateQuestion(question, "updateTitle", "updateContent", updateQUestionType);
        assertThat(question.getTitle()).isEqualTo("updateTitle");
    }

    @Test
    public void deleteQuestion() {
        QuestionType questionType = questionService.findByQuestionType(ACCOUNT);
        Question question = new Question("abc", "abc", questionType, null);
        questionService.save(question);
        questionService.deleteQuestion(question);

        assertThrows(IllegalArgumentException.class, () -> questionService.findById(question.getId()));
    }

}