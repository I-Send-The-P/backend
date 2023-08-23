package ISTP.repository;

import ISTP.domain.help.question.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {

    QuestionType findByQuestionType(String questionTypeName);
}
