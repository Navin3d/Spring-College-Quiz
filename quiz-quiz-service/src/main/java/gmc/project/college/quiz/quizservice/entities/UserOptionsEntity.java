package gmc.project.college.quiz.quizservice.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_options")
public class UserOptionsEntity implements Serializable {

	private static final long serialVersionUID = -3447741191995284089L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne
	private UserEntity user;
	
	@OneToOne
	private QuestionEntity question;
	
	@OneToOne
	private OptionEntity option;
	
}
