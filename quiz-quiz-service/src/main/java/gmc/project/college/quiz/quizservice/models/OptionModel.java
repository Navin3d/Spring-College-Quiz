package gmc.project.college.quiz.quizservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class OptionModel implements Serializable {

	private static final long serialVersionUID = -8442173786587814492L;
	
	private String optionId;
	
	private String value;
	
	private Boolean isCorrect;

}
