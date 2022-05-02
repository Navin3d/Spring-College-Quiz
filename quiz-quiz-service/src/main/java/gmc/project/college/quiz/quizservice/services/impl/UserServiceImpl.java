package gmc.project.college.quiz.quizservice.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.college.quiz.quizservice.daos.UserDao;
import gmc.project.college.quiz.quizservice.entities.UserEntity;
import gmc.project.college.quiz.quizservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserEntity findOne(String userName) {
		UserEntity returnValue = null;
		
		if(userName.contains("@")) 
			returnValue = userDao.findByEmail(userName);
		else if(userName.startsWith("USR-"))
			returnValue = userDao.findByUserId(userName);
		else
			returnValue = userDao.findByRollNo(userName);
		
		return returnValue;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		List<UserEntity> returnValue = userDao.findAll();
		return returnValue;
	}	

}
