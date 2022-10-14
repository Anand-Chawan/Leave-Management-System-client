package com.achawan.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private static final String LOGIN_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGOUT_SUCCESS_URL = "/login";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.requestMatchers(SecurityUtils::IsUserLoggedIn).permitAll()
		.and().formLogin().loginPage(LOGIN_URL).permitAll()
		.loginProcessingUrl(LOGIN_URL)
		.failureUrl(LOGIN_FAILURE_URL)
		.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}
	
}
