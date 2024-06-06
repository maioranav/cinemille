package org.vm93.cinemille.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vm93.cinemille.model.User;
import org.vm93.cinemille.repo.UserRepo;

@Configuration
public class AdminConfig {

	@Autowired
	UserRepo repo;

	@Value("${user.admin.name}")
	private String name;
	@Value("${user.admin.surname}")
	private String surname;
	@Value("${user.admin.email}")
	private String email;
	@Value("${user.admin.username}")
	private String username;
	@Value("${user.admin.password}")
	private String password;

	@Bean
	@Scope("prototype")
	User adminGenerator() {
		PasswordEncoder pe = new BCryptPasswordEncoder();
		User u = User.builder().name(name).surname(surname).email(email).username(username)
				.password(pe.encode(password)).build();

		return u;
	}

}