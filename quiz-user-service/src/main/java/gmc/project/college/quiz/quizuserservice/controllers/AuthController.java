package gmc.project.college.quiz.quizuserservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.services.AuthService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;	
	@Autowired
	private Environment environment;

	@GetMapping(path = "/server/status/check")
	private String getStatus() {
		return "The instance runs at port: " + environment.getProperty("local.server.port");
	}
	
	@PostMapping(path = "/auth/signup")
	private ResponseEntity<UserEntity> addManyStudents(@RequestBody UserEntity newUsers) {
		UserEntity returnValue = authService.createUsers(newUsers);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
		
}
