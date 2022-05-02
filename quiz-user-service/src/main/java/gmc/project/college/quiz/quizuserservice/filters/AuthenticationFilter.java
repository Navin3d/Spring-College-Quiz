package gmc.project.college.quiz.quizuserservice.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import gmc.project.college.quiz.quizuserservice.config.AuthConfig;
import gmc.project.college.quiz.quizuserservice.entities.UserEntity;
import gmc.project.college.quiz.quizuserservice.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthService authService;
	private final AuthConfig authConfig;
	
	public AuthenticationFilter(AuthService authService, AuthConfig authConfig, AuthenticationManager authManager) {
		super.setAuthenticationManager(authManager);
		this.authService = authService;
		this.authConfig = authConfig;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		UserEntity creds;
		
		try {
			creds = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
			return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
								creds.getRollNo(), 
								creds.getEncryptedPassword()
							)
					);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String secret = Base64.getEncoder().encodeToString(authConfig.getTokenSecret().getBytes(StandardCharsets.UTF_8));
		
		String userName = ((User)authResult.getPrincipal()).getUsername();
		
		UserEntity foundUser = authService.findOne(userName);
		
		String token = Jwts.builder()
				.setIssuer(authConfig.getIssuer())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + authConfig.getTokenExpiration()))
				.setSubject(foundUser.getUserId())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
		
		response.setHeader("Authorization", token);
		response.setHeader("IsStudent", ""+foundUser.getIsStudent());
		response.setHeader("UserId", foundUser.getUserId());
		
	}
	
}
