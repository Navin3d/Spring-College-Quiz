package gmc.project.college.quiz.quizgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class QuizGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizGatewayServiceApplication.class, args);
	}

}
