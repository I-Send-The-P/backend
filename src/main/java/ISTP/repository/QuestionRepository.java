package ISTP.repository;

import ISTP.domain.help.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByOrderByCreateDateDesc();

    List<Question> findAllByMemberIdOrderByCreateDateDesc(@Param("memberId") Long memberId);

    void deleteByMemberId(Long memberId);

    List<Question> findAllByMemberIdAndQuestionTypeIdOrderByCreateDateDesc(Long memberId, Long questionTypeId);
}
