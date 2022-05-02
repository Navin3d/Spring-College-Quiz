package gmc.project.college.quiz.quizservice.controllers;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.college.quiz.quizservice.entities.QuestionEntity;
import gmc.project.college.quiz.quizservice.models.QuestionModel;
import gmc.project.college.quiz.quizservice.services.ExcelAssist;
import gmc.project.college.quiz.quizservice.services.QuestionService;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

	@Autowired
	private Environment environment;
	@Autowired
	private QuestionService questionService;
	
	@GetMapping(path = "/server/status/check")
	public String getServerStatus() {
		return "Server Running In Port: " + environment.getProperty("local.server.port");
	}
	
	@GetMapping(path = "/questions")
	public ResponseEntity<List<QuestionModel>> getAllQuestions() {
		List<QuestionModel> returnValue = questionService.getAllQuestions();

		return ResponseEntity.status(HttpStatus.OK).body(returnValue);
	}
	
	@GetMapping(path = "/{userId}/{questionId}/{optionId}")
	public ResponseEntity<String> chooseUserAnswer(@PathVariable String userId, @PathVariable String questionId, @PathVariable String optionId) {
		questionService.answerQuestion(userId, questionId, optionId);
		return ResponseEntity.status(HttpStatus.OK).body("Answer Recorded...");
	}
	
	@PostMapping(path = "/question/upload")
	public ResponseEntity<List<QuestionModel>> uploadQuestion(@RequestParam("file") MultipartFile file) {
		List<QuestionModel> returnValue = null;

		if (ExcelAssist.hasExcelFormat(file))
			returnValue = questionService.addManyQuestions(file);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	@GetMapping(path = "/questions/deleteall")
	public ResponseEntity<String> deleteAllQuestions() {
		questionService.deleteAllQuestions();

		return ResponseEntity.status(HttpStatus.OK).body("All the Questions has been deleted...");
	}
}
