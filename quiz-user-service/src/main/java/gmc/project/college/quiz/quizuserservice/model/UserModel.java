package gmc.project.college.quiz.quizuserservice.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserModel implements Serializable {
	
	private static final long serialVersionUID = -4149049111989313272L;
	
	private String userId;
	
	private String initial;
	
	private String firstName;
	
	private String lastName;
	
	private String rollNo;
	
	private String classAndSection;
	
	private String email;
	
	private String password;
	
	private String encryptedPassword;

}
