package ISTP.service;

import ISTP.domain.board.Board;
import ISTP.domain.board.BoardType;
import ISTP.domain.help.question.InquiryStatus;
import ISTP.domain.help.question.InquiryType;
import ISTP.domain.help.question.Question;
import ISTP.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long save(Question question) {
        Question saveQuestion = questionRepository.save(question);
        log.info("{} 1:1 문의글 생성", saveQuestion.getTitle());
        return saveQuestion.getId();
    }

    public Question findById(Long questionId) {
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문의입니다"));
        log.info("아이디로 문의 찾기 {}", findQuestion);
        return findQuestion;
    }

    public List<Question> findAll() {
        log.info("모든 문의 조회 나중에 작성된 시간 순으로 조회");
        return questionRepository.findAllByOrderByCreateDateDesc();
    }

    @Transactional
    public void changeStatus(Question question) {
        log.info("답변을 받아 문의상태를 문의중에서 문의완료로 변경");
        question.changeStatus();
    }
    @Transactional
    public void updateQuestion(Question question, String updateTitle, String updateContent, InquiryType updateInquiryType) {
        if(question.getStatus().equals(InquiryStatus.문의완료)) {
            log.info("이미 문의완료 상태이기에 수정할 수 없음");
            return;
        }
        question.updateQuestion(updateTitle, updateContent, updateInquiryType);
        log.info("문의글 수정 완료 {}", question);
    }
    @Transactional
    public void deleteQuestion(Question question) {
        log.info("{} 문의글 삭제", question.getId());
        questionRepository.delete(question);
    }

}
