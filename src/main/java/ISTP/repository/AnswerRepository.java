package ISTP.repository;

import ISTP.domain.help.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByQuestionId(Long questionId);
    List<Answer> findV2ByQuestionId(Long questionId);

    void deleteByMemberId(Long memberId);
}
