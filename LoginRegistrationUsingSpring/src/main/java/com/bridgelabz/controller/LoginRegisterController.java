package com.bridgelabz.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserServiceImplementation;
import com.bridgelabz.util.Utility;

/**
 * purpose-It is controller class and it's uset to controll the login and
 * registration of user.It is the media between View and Model.
 * 
 * @author yuga
 * @since 08-07-2018
 */
@RestController
public class LoginRegisterController {
	@Autowired
	UserServiceImplementation userServiceImplementation;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> loginUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		if (userServiceImplementation.verifyUser(user) == 1) {
			return new ResponseEntity("Welcome " + user.getUserName(), HttpStatus.OK);
		}
		if (userServiceImplementation.verifyUser(user) == 0) {
			return new ResponseEntity(new Utility("Password is incorrect"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity(new Utility("Username doesnt not exist"), HttpStatus.CONFLICT);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) throws ClassNotFoundException, SQLException {
		if (!userServiceImplementation.verifyEmail(user)) {
			userServiceImplementation.saveUser(user);
			return new ResponseEntity("User successfully registered", HttpStatus.OK);
		}
		return new ResponseEntity(new Utility("Email-id already exist!!"), HttpStatus.CONFLICT);
	}

}
