package gmc.project.college.quiz.quizservice.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class QuestionEntity implements Serializable {
	
	private static final long serialVersionUID = -8262862968699291799L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "question_id")
	private String questionId;
	
	@Column(name = "value")
	private String value;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private Set<OptionEntity> options = new HashSet<>();

}
