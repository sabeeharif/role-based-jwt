package com.security.jwt.medium.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.security.jwt.medium.config.CustomUserDetailsService;
import com.security.jwt.medium.config.JwtHelper;
import com.security.jwt.medium.config.WebSecurityConfig;
import com.security.jwt.model.LoginResult;

@CrossOrigin(origins = { "${app.security.cors.origin}" })
@RestController
public class AuthController {

	private final JwtHelper jwtHelper;
//	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	public AuthController(JwtHelper jwtHelper, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.jwtHelper = jwtHelper;
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping(path = "login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public LoginResult login(@RequestParam String username, @RequestParam String password) {

		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			Map<String, String> claims = new HashMap<>();
			claims.put("username", username);

			String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(" "));
			claims.put(WebSecurityConfig.AUTHORITIES_CLAIM_NAME, authorities);
			claims.put("userId", String.valueOf(1));

			String jwt = jwtHelper.createJwtForClaims(username, claims);
			return new LoginResult(jwt);
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
	}
}