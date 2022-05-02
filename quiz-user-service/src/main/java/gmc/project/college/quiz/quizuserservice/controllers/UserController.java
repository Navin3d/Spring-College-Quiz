package gmc.project.college.quiz.quizuserservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.model.UserModel;
import gmc.project.college.quiz.quizuserservice.services.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserEntity> updateUser(@RequestBody UserModel user) {
		UserEntity updatedUser = userService.updatedUser(user);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}
	
	@GetMapping(path = "/{userName}/delete")
	public ResponseEntity<String> deleteUSer(@PathVariable String userName) {
		userService.deleteAUser(userName);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted...");
	}
	
}
