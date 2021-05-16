package org.jp.spring.security.in.memory.security.config;

import java.util.Arrays;

import org.jp.spring.security.in.memory.constants.AppConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetails user1(final BCryptPasswordEncoder bCryptPasswordEncoder) {
		return User.builder().username(AppConstant.USER).password(bCryptPasswordEncoder.encode(AppConstant.PASS))
				.authorities(Arrays.asList()).build();
	}

	@Bean
	public UserDetails user2(final BCryptPasswordEncoder bCryptPasswordEncoder) {
		return User.builder().username("user2").password(bCryptPasswordEncoder.encode("pass2"))
				.authorities(Arrays.asList()).build();
	}

	@Bean
	public UserDetailsService userDetailsService1(final UserDetails user1,final UserDetails user2) {
		final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
		return inMemoryUserDetailsManager;
	}

	@Bean
	public UserDetailsService userDetailsService2(final UserDetails user2) {
		final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user2);
		return inMemoryUserDetailsManager;
	}

}
