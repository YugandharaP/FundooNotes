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

@RestController
public class LoginRegisterController {

	@Autowired
	UserServiceImplemntation userServiceImplementation;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) {
		if (!userServiceImplementation.verifyUserByuserName(user).get().equals(null)) {
			return new ResponseEntity("Welcome " + user.getFirstName()+" "+user.getLastName(), HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Username doesnt not exist"), HttpStatus.CONFLICT);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (!userServiceImplementation.verifyEmail(user)) {
			userServiceImplementation.saveUser(user);
			return new ResponseEntity("User successfully registered", HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("UserName allready exists"), HttpStatus.CONFLICT);
	}

}
