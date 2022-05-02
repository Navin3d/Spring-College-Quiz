package gmc.project.college.quiz.quizservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.college.quiz.quizservice.entities.OptionEntity;

public interface OptionDao extends JpaRepository<OptionEntity, Long> {
	OptionEntity findByOptionId(String optionId);
}
