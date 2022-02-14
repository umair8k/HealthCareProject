package com.hc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hc.service.PatientDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PatientDetailsService patientDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(patientDetailsService);
		/*.inMemoryAuthentication()
        .withUser("user").password("password").roles("USER");*/}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*  http.httpBasic().disable();
		 http
	     .authorizeRequests()
	     .antMatchers("post/getStatus/{userName}/**")
	          .access("@userSecurity.hasUserName(authentication,#userName)");*/
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/patient/signup").permitAll().and().authorizeRequests()
		.antMatchers("/patient/**","/appointment/**").authenticated().and().httpBasic();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
