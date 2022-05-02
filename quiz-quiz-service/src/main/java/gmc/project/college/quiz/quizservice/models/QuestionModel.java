package gmc.project.college.quiz.quizservice.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class QuestionModel implements Serializable {
	
	private static final long serialVersionUID = 4882936439116851623L;
	
	private String questionId;
	
	private String value;
	
	private Set<OptionModel> options = new HashSet<>();

}
