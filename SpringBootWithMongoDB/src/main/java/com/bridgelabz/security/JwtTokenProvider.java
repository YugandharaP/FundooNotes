package com.bridgelabz.security;

import java.security.Key;
import java.sql.Date;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bridgelabz.controller.UserController;
import com.bridgelabz.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * purpose-To generate token for intended user
 * 
 * @author yuga
 *
 */
@Service
public class JwtTokenProvider {
	Key key= MacProvider.generateKey();
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	/**
	 * To generate the token
	 * @param user
	 * @return
	 */
	public String generator(User user) {
		String email = user.getEmail();
		return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS256, key).compact();
	}

	/**
	 * To get claims of the token 
	 * @param jwt
	 * @return 
	 */
	public String parseJWT(String jwtToken) {
		logger.info(jwtToken);
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
		logger.info("Subject: " + claims.getSubject());
		return claims.getSubject();
	}
}
