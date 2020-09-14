package com.numbergenerator.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NumberGeneratorWebSecurity extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(SecurityConstants.USER_NAME).password(SecurityConstants.PASSWORD)
				.roles(SecurityConstants.PRODUCTS_ROLE);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests().antMatchers("/actuator").permitAll().antMatchers("/h2-console").permitAll()
				.antMatchers("/swagger").permitAll().antMatchers(SecurityConstants.PRODUCTS_END_POINT)
				.hasRole(SecurityConstants.PRODUCTS_ROLE).and().httpBasic();

	}

}
