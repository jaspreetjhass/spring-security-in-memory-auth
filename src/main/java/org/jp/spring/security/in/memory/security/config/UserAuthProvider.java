package org.jp.spring.security.in.memory.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
public class UserAuthProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService1;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final String userName = authentication.getName();
		final UserDetails userDetails = userDetailsService1.loadUserByUsername(userName);
		final String password = authentication.getCredentials().toString();
		if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(userName, password, Arrays.asList());
		}
		throw new AuthenticationCredentialsNotFoundException("credentials are not found");
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
