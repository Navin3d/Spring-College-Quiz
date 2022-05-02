package gmc.project.college.quiz.quizservice.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gmc.project.college.quiz.quizservice.entities.QuestionEntity;
import gmc.project.college.quiz.quizservice.models.QuestionModel;

public interface QuestionService {
	List<QuestionModel> addManyQuestions(MultipartFile file);
	List<QuestionModel> getAllQuestions();
	
	void answerQuestion(String userId, String questionId, String optionId);
	void deleteAllQuestions();
}
