package gmc.project.college.quiz.quizservice.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.college.quiz.quizservice.entities.QuestionEntity;
import gmc.project.college.quiz.quizservice.entities.UserEntity;
import gmc.project.college.quiz.quizservice.entities.UserOptionsEntity;

@Transactional
public interface UserOptionsDao extends JpaRepository<UserOptionsEntity, Long> {
	List<UserOptionsEntity> findByUser(UserEntity user);
	UserOptionsEntity findByQuestion(QuestionEntity question);
}
