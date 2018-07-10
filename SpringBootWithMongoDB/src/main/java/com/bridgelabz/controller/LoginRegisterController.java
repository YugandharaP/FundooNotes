package com.bridgelabz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.security.JwtTokenProvider;
import com.bridgelabz.service.UserServiceImplemntation;
import com.bridgelabz.util.Utility;

/**purpose-To interact with the view and services.controller is the media between view and model.
 * @author yuga
 *@since 09-07-2018
 */
@RestController
public class LoginRegisterController {

	@Autowired
	private UserServiceImplemntation userServiceImplementation;
	@Autowired
	private JwtTokenProvider token;
	private static final Logger logger = LoggerFactory.getLogger(LoginRegisterController.class);

	/**To take login url from view and perform operations on that
	 * @param user
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		if (!userServiceImplementation.verifyUserByuserName(user).get().equals(null)) {
			String validToken = token.generator(user);
			logger.info("Your token is : " + validToken);
			token.parseJWT(validToken);
			return new ResponseEntity("Welcome " + user.toString(), HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Username doesnt not exist"), HttpStatus.CONFLICT);
	}

	/**To take register url from view and perform operations on that
	 * @param user
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (!userServiceImplementation.verifyEmail(user)) {
			String validToken = token.generator(user);
			logger.info("Your token is : " + validToken);
			token.parseJWT(validToken);
			userServiceImplementation.saveUser(user);
			return new ResponseEntity("User successfully registered", HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("UserName allready exists"), HttpStatus.CONFLICT);
	}

}
