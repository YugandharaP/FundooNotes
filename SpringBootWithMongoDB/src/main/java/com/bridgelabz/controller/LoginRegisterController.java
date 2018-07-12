package com.bridgelabz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserServiceImplemntation;
import com.bridgelabz.util.Utility;

/**
 * purpose-To interact with the view and services.controller is the media
 * between view and model.
 * 
 * @author yuga
 * @since 09-07-2018
 */
@RestController
public class LoginRegisterController {
	@Autowired
	private UserServiceImplemntation userServiceImplementation;

	/**
	 * To take login url from view and perform operations on that
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		if (!userServiceImplementation.findByUserName(user).get().equals(null)) {
			return new ResponseEntity("Welcome " + user.getFirstName(), HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Username doesnt not exist"), HttpStatus.CONFLICT);
	}

	/**
	 * To take register url from view and perform operations on that
	 * 
	 * @param user
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (userServiceImplementation.verifyUser(user)) {
			if (userServiceImplementation.sendAuthenticationMail(user)) {
				return new ResponseEntity("Activation link send on your email", HttpStatus.OK);
			}
		}
		return new ResponseEntity(new Utility("UserName allready exists"), HttpStatus.CONFLICT);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/useractivation", method = RequestMethod.GET)
	public ResponseEntity<String> userActivation(@RequestBody User user) {
		return new ResponseEntity(new Utility("Register Successfully"), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/forgetpassword", method = RequestMethod.POST)
	public ResponseEntity<User> forgetPassword(@RequestBody User user) {
		System.out.println("into url");
		if (userServiceImplementation.verifyEmail(user)) {
			return new ResponseEntity("We are sending you password on your email.Please check your email",
					HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Mail Id Not found on our database"), HttpStatus.CONFLICT);
	}

}
