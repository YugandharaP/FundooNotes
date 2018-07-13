package com.bridgelabz.configuration;

import org.springframework.context.annotation.Configuration;

import com.bridgelabz.security.JwtTokenProvider;

@Configuration
public class UserConfig {
	//@Bean
	public JwtTokenProvider jwtToken() {
		return new JwtTokenProvider();
	}
}
