package gmc.project.college.quiz.quizuserservice.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -1085347173758612014L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "initial")
	private String initial;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "roll_no")
	private String rollNo;
	
	@Column(name = "class_section")
	private String classAndSection;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "encrypted_password")
	private String encryptedPassword;
	
	@Column(name = "total_marks")
	private Integer totalMarks = 0;
	
	@Column(name = "is_student")
	private Boolean isStudent = true;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt = Timestamp.from(Instant.now());

}
