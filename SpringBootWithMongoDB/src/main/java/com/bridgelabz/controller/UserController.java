package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.security.JwtTokenProvider;
import com.bridgelabz.service.IUserService;
import com.bridgelabz.util.Utility;

import io.swagger.annotations.ApiOperation;

/**
 * purpose-To interact with the view and services.controller is the media
 * between view and model.
 * 
 * @author yuga
 * @since 09-07-2018
 */
@RestController
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	ApplicationContext contextFactory;

	@Autowired
	JwtTokenProvider tokenProvider;
	/**
	 * To take login url from view and perform operations on that
	 * 
	 * @param user
	 * @return Status
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "login")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		if (!userService.findByUserName(user).get().equals(null)) {
			User optionalUser = userService.findByUserName(user).get();
			return new ResponseEntity("Welcome " + optionalUser.getFirstName() + " " + optionalUser.getLastName(),
					HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Username doesnt not exist"), HttpStatus.CONFLICT);
	}

	/**
	 * To take register url from view and perform operations on that
	 * 
	 * @param user
	 * @return Status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (userService.verifyUser(user)) {
			return new ResponseEntity("Activation link send on your email", HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("UserName allready exists"), HttpStatus.CONFLICT);
	}

	/**
	 * To take activationlink url from view and perform operations on that
	 * 
	 * @param user
	 * @return Status
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/useractivation", method = RequestMethod.GET)
	public ResponseEntity<String> userActivation(HttpServletRequest req) {
		String token= req.getQueryString();
		System.out.println(token);
		String email = tokenProvider.parseJWT(token);
		if(userService.setActivationStatus(email)) {
			return new ResponseEntity("Register Successfully", HttpStatus.OK);
		}
		//JwtTokenProvider email = contextFactory.getBean(JwtTokenProvider.class);
		return new ResponseEntity(new Utility("Activation process is not complete"), HttpStatus.CONFLICT);
	}

	/**
	 * To take forget password url from view and perform operations on that
	 * 
	 * @param user
	 * @return Status
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST)
	public ResponseEntity<User> forgetPassword(@RequestBody User user) {
		System.out.println("into url");
		if (userService.verifyEmail(user)) {
			return new ResponseEntity("We are sending you password on your email.Please check your email",
					HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Mail Id Not found on our database"), HttpStatus.CONFLICT);
	}
}
