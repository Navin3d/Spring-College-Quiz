package gmc.project.college.quiz.quizuserservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

	private String filterProcessUrl;
	
	private String tokenSecret;
	
	private Long tokenExpiration;
	
	private String issuer;
	
}
