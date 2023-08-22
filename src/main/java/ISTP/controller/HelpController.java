package ISTP.controller;

import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import ISTP.dtos.help.QuestionEditForm;
import ISTP.dtos.help.QuestionSaveForm;
import ISTP.dtos.help.QuestionSummaryDto;
import ISTP.service.AnswerService;
import ISTP.service.MemberService;
import ISTP.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/help")
@Slf4j
public class HelpController {

    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    //1:1 문의 글 작성
    /**
     * 로그인 세션에서 회원 정보 가져오는 기능 추가 구현해야할듯~~ 우선은 그냥 파라미터로 멤버아이디 하나 임의로 받겠습니다
     */
    @PostMapping("/create")
    public Long save(@Validated @RequestBody QuestionSaveForm form, BindingResult bindingResult, @RequestParam Long memberId) {
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //에러처리 어케 할까여
            throw new IllegalArgumentException("문의글 작성 시 오류 발생");
        }
        Member member = memberService.findById(memberId);
        Question question = new Question(form.getTitle(), form.getContent(), form.getInquiryType(), member);
        questionService.save(question);
        return question.getId();
    }

    //1:1 문의내역 리스트
    @ResponseBody
    @GetMapping("/list")
    public List<QuestionSummaryDto> questionList() {
        List<Question> questions = questionService.findAll();
        List<QuestionSummaryDto> questionSummaryDtos = new ArrayList<>();
        for(Question question : questions) {
            QuestionSummaryDto questionSummaryDto = new QuestionSummaryDto(question);
            questionSummaryDtos.add(questionSummaryDto);
        }
        return questionSummaryDtos;
    }

    //문의 완료가 되지 않은 상태에서 1:1 문의글 수정하기
    @PostMapping("/{questionId}/edit")
    public Long editQuestion(@Validated @RequestBody QuestionEditForm form, BindingResult bindingResult, @PathVariable Long questionId) {
        if(bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //에러처리 어케 할까여
            throw new IllegalArgumentException("문의글 수정 시 오류 발생");
        }
        Question question = questionService.findById(questionId);
        questionService.updateQuestion(question, form.getTitle(), form.getContent(), form.getInquiryType());
        return question.getId();
    }
}
