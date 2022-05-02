package gmc.project.college.quiz.quizservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.college.quiz.quizservice.entities.QuestionEntity;

public interface QuestionDao extends JpaRepository<QuestionEntity, Long> {
	QuestionEntity findByQuestionId(String questionId);
}
