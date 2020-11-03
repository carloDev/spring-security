package com.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AppSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // disabilita il csrf attack
			.authorizeRequests()
			.antMatchers("/", "index", "js", "css", "images").permitAll() // permetti il traffico verso / e verso index, ecc
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails userAdmin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.roles("ADMIN") // ROLE_ADMIN
				.build(); 
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("user"))
				.roles("USER") // ROLE_USER
				.build(); 
		
		return new InMemoryUserDetailsManager(
				userAdmin,
				user
		);
		
	}
	
	
	
	

}
