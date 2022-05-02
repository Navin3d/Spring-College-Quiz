package gmc.project.college.quiz.quizuserservice.services;

import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.model.UserModel;

public interface UserService {
	
	UserEntity updatedUser(UserModel updatedUser);
	void deleteAUser(String userName);
	UserEntity findOne(String userName);

}
