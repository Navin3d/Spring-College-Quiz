package gmc.project.college.quiz.quizuserservice.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import gmc.project.college.quiz.quizuserservice.entities.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByUserId(String userId);
	UserEntity findByRollNo(String rollNo);
	UserEntity findByEmail(String email);

}
