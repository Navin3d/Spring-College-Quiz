package gmc.project.college.quiz.quizuserservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gmc.project.college.quiz.quizuserservice.filters.AuthenticationFilter;
import gmc.project.college.quiz.quizuserservice.services.AuthService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthConfig authConfig;
	@Autowired
	private AuthService authService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.headers().frameOptions().disable()
		.and()
			.csrf().disable();
		
		http.addFilter(getAuthenticationFilter());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
	}
	
	public AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(authService, authConfig, authenticationManager());
		authFilter.setFilterProcessesUrl(authConfig.getFilterProcessUrl());
		return authFilter;
	}
	
}
