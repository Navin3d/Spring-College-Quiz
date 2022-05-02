package gmc.project.college.quiz.quizservice.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gmc.project.college.quiz.quizservice.daos.OptionDao;
import gmc.project.college.quiz.quizservice.daos.QuestionDao;
import gmc.project.college.quiz.quizservice.daos.UserDao;
import gmc.project.college.quiz.quizservice.daos.UserOptionsDao;
import gmc.project.college.quiz.quizservice.entities.OptionEntity;
import gmc.project.college.quiz.quizservice.entities.QuestionEntity;
import gmc.project.college.quiz.quizservice.entities.UserEntity;
import gmc.project.college.quiz.quizservice.entities.UserOptionsEntity;
import gmc.project.college.quiz.quizservice.models.QuestionModel;
import gmc.project.college.quiz.quizservice.services.ExcelAssist;
import gmc.project.college.quiz.quizservice.services.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private OptionDao optionDao;
	@Autowired
	private UserOptionsDao userOptionsDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<QuestionModel> addManyQuestions(MultipartFile file) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<QuestionEntity> savedData = null;

		try {

			List<QuestionEntity> questions = ExcelAssist.excelToTutorials(file.getInputStream());

			savedData = questionDao.saveAll(questions);
			
			savedData.stream().iterator()
				.forEachRemaining(question -> {
					question.getOptions().stream().iterator()
							.forEachRemaining(option -> {
								option.setQuestion(question);
								optionDao.save(option);
							});
				});

		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
		
		List<QuestionModel> returnValue = new ArrayList<>();
		
		savedData.stream().iterator()
				.forEachRemaining(question -> {
					returnValue.add(modelMapper.map(question, QuestionModel.class));
				});

		return returnValue;

	}

	@Override
	public void deleteAllQuestions() {
		questionDao.deleteAll();
	}

	@Override
	public List<QuestionModel> getAllQuestions() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<QuestionModel> returnValue = new ArrayList<>();
		
		questionDao.findAll().stream().iterator()
					.forEachRemaining(question -> {
						returnValue.add(modelMapper.map(question, QuestionModel.class));
					});
				
		return returnValue;
	}

	@Override
	public void answerQuestion(String userId, String questionId, String optionId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity foundUser = userDao.findByUserId(userId);
		QuestionEntity foundQuestion = questionDao.findByQuestionId(questionId);
		OptionEntity foundOption= optionDao.findByOptionId(optionId);
		
		if(foundUser == null || foundQuestion == null || foundOption == null)
			throw new RuntimeException("Question or Option or User Not Found...");
				
		List<UserOptionsEntity> userOldAnswer = userOptionsDao.findByUser(foundUser);
				
		UserOptionsEntity foundAnswer = new UserOptionsEntity();
		
		userOldAnswer.stream().iterator()
				.forEachRemaining(answer -> {
					if(answer.getQuestion().equals(foundQuestion))
						modelMapper.map(answer, foundAnswer);
				});
		
		if(foundAnswer.getId() == null) {		
			UserOptionsEntity userOption = new UserOptionsEntity();
			userOption.setUser(foundUser);
			userOption.setQuestion(foundQuestion);
			userOption.setOption(foundOption);
			userOptionsDao.save(userOption);
		} else {
			foundAnswer.setOption(foundOption);
			userOptionsDao.save(foundAnswer);
		}
		
	}

}
