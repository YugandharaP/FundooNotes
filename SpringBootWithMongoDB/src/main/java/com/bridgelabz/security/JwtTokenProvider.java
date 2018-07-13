package com.bridgelabz.security;

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
/**purpose-To generate token for intended user
 * @author yuga
 *
 */
@Service
public class JwtTokenProvider {
	final static String KEY = "yuga";
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**To generate the token
	 * @param user
	 * @return
	 */
	public String generator(User user) {
		String email = user.getEmail();
		String passkey = user.getPassword();
		long time = System.currentTimeMillis();
		long nowMillis = System.currentTimeMillis() + (20 * 60 * 60 * 1000);
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(passkey).setIssuedAt(now).setSubject(email)
				.signWith(SignatureAlgorithm.HS256, KEY);
		if (time >= 0) {

		}
		return builder.compact();
	}

	/**To claim token
	 * @param jwt
	 */
	public void parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwt)
				.getBody();
		logger.info("ID: " + claims.getId());
		logger.info("Subject: " + claims.getSubject());
	}
}
