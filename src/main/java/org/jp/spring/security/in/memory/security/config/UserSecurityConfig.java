package org.jp.spring.security.in.memory.security.config;

import java.util.Arrays;
import java.util.List;

import org.jp.spring.security.in.memory.constants.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Order(0)
@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsManager(bCryptPasswordEncoder()))
				.passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.antMatcher("/in-memory/**").formLogin().and().authorizeRequests().mvcMatchers("/in-memory/user")
				.hasRole(AppConstant.USER).mvcMatchers("/in-memory/health").permitAll().mvcMatchers("/in-memory/**")
				.hasRole(AppConstant.ADMIN);
	}

	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager(final BCryptPasswordEncoder bCryptPasswordEncoder) {
		final UserDetails userDetails = User.withUsername(AppConstant.USER)
				.password(bCryptPasswordEncoder.encode(AppConstant.PASS)).authorities(AppConstant.ROLE_USER).build();
		final UserDetails adminDetails = User.withUsername(AppConstant.ADMIN)
				.password(bCryptPasswordEncoder.encode(AppConstant.ADMIN))
				.authorities(AppConstant.ROLE_USER, AppConstant.ROLE_ADMIN).build();
		final UserDetails anonymousDetails = User.withUsername(AppConstant.ANONYMOUS)
				.password(bCryptPasswordEncoder.encode(AppConstant.PASS)).authorities(Arrays.asList()).build();

		final List<UserDetails> userDetailList = Arrays.asList(userDetails, adminDetails, anonymousDetails);
		final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(userDetailList);
		return inMemoryUserDetailsManager;
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
