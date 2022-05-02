package gmc.project.college.quiz.quizservice.services;

import java.util.List;

import gmc.project.college.quiz.quizservice.entities.UserEntity;

public interface UserService {

	UserEntity findOne(String userName);
	List<UserEntity> getAllUsers();
	
}
