package ISTP.service;


import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
import ISTP.domain.member.Member;
import ISTP.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;
    @Transactional
    public Long save(Answer answer) {
        Answer saveAnswer = answerRepository.save(answer);
        log.info("{} 1:1 문의 답변글 생성", saveAnswer);
        return saveAnswer.getId();
    }

    @Transactional
    public Answer createAnswer(String content, Member member, Question question) {
        log.info("답변을 받아 문의상태를 문의중에서 문의완료로 변경");
        question.changeStatus();
        Answer answer = new Answer(content, member, question);
        return answer;
    }

    public Answer findById(Long answerId) {
        Answer findAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 답변입니다."));
        log.info("아이디로 답변 찾기 {}", findAnswer);
        return findAnswer;
    }

    public List<Answer> findAll() {
        log.info("모든 답변 조회");
        return answerRepository.findAll();
    }


}
