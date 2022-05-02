package gmc.project.college.quiz.quizservice.daos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.college.quiz.quizservice.entities.UserEntity;

@Transactional
public interface UserDao extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByUserId(String userId);
	UserEntity findByRollNo(String rollNo);
	UserEntity findByEmail(String email);
	
}
