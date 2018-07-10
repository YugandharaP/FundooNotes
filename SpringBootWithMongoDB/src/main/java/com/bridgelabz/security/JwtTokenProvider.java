package com.bridgelabz.security;

import java.sql.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

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

	/**To generate the token
	 * @param user
	 * @return
	 */
	public String generator(User user) {
		String userName = user.getUserName();
		String passkey = user.getPassword();
		long time = System.currentTimeMillis();
		long nowMillis = System.currentTimeMillis() + (20 * 60 * 60 * 1000);
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(passkey).setIssuedAt(now).setSubject(userName)
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
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
	}
}
