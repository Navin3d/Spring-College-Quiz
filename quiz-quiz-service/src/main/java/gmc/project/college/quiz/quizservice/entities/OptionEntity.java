package gmc.project.college.quiz.quizservice.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "options")
public class OptionEntity implements Serializable {
	
	private static final long serialVersionUID = -8262862968699291799L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "option_id")
	private String optionId;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "is_correct")
	private Boolean isCorrect;
	
	@ManyToOne
	private QuestionEntity question;
	
}
