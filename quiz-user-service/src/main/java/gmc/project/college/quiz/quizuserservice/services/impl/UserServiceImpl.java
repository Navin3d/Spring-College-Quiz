package gmc.project.college.quiz.quizuserservice.services.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.project.college.quiz.quizuserservice.daos.UserDao;
import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.model.UserModel;
import gmc.project.college.quiz.quizuserservice.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserEntity findOne(String userName) {
		UserEntity returnValue = null;
		
		if(userName.contains("@")) 
			returnValue = userDao.findByEmail(userName);
		else 
			returnValue = userDao.findByUserId(userName);
		
		return returnValue;
	}

	@Override
	public UserEntity updatedUser(UserModel updatedUser) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity detachedUser = findOne(updatedUser.getUserId());
		modelMapper.map(updatedUser, detachedUser);
		
		UserEntity returnValue = userDao.save(detachedUser);
	
		return returnValue;
	}

	@Override
	public void deleteAUser(String userName) {
		
		UserEntity userToDelete = findOne(userName);
		
		userDao.delete(userToDelete);
		
	}

}
