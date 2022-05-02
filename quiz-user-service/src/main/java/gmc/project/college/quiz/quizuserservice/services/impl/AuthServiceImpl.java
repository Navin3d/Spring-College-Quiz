package gmc.project.college.quiz.quizuserservice.services.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gmc.project.college.quiz.quizuserservice.daos.UserDao;
import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity foundUser = findOne(username);
		
		return new User(foundUser.getUserId(), foundUser.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserEntity createUsers(UserEntity newUsers) {
		
		newUsers.setUserId("USR-" + UUID.randomUUID());
		newUsers.setEncryptedPassword(passwordEncoder.encode(newUsers.getEncryptedPassword()));
		newUsers.setIsStudent(true);
		newUsers.setTotalMarks(0);
		newUsers.setCreatedAt(Timestamp.from(Instant.now()));
		UserEntity returnValue = userDao.save(newUsers);
		
		return returnValue;
	}

	@Override
	public UserEntity findOne(String userName) {
		UserEntity returnValue = null;
		
		if(userName.contains("@"))
			returnValue = userDao.findByEmail(userName);
		else if(userName.startsWith("USR-"))
			returnValue = userDao.findByUserId(userName);
		else
			returnValue = userDao.findByRollNo(userName);
		
		if(returnValue == null) 
			throw new RuntimeException("User with UserId: " + userName + " not found.");
		
		return returnValue;
	}

}
