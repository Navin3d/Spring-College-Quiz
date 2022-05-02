package gmc.project.college.quiz.quizuserservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import gmc.project.college.quiz.quizuserservice.entities.UserEntity;

public interface AuthService extends UserDetailsService {

	UserEntity createUsers(UserEntity newUsers);
	UserEntity findOne(String userName);
	
}
