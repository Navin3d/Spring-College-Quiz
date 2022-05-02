package gmc.project.college.quiz.quizdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class QuizDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizDiscoveryServiceApplication.class, args);
	}

}
