package com.bridgelabz.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.controller.UserController;
import com.bridgelabz.model.User;
import com.bridgelabz.repository.IUserRepository;
import com.bridgelabz.security.JwtTokenProvider;
import com.bridgelabz.util.Utility;

/**
 * purpose-To connect controller and MongoRepository
 * 
 * @author yuga
 * @since 09-07-2018
 */
@Service
public class UserServiceImplemntation implements IUserService {
	@Autowired
	IUserRepository userRepository;
	@Autowired
	private JwtTokenProvider token;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final String adminMailId = "dharaparanjape.1007@gmail.com";
	private final String adminPassword = "dhara1007";

	/**
	 * To verify userName is present in database or not
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<User> findByUserName(User user) {
		@SuppressWarnings("rawtypes")
		Optional optionalUser = userRepository.findByEmail(user.getEmail());
		if (optionalUser.isPresent()) {
			return optionalUser;
		} else
			return null;
	}

	/** To verify user which is present in database or not */
	@Override
	public boolean verifyUser(User user) {
		String userEmail = user.getEmail();
		logger.info(userEmail);
		if (userRepository.findByEmail(user.getEmail()) != null) {
			saveUser(user);
			return true;
		}
		return false;
	}

	/** To verify email which is present in database or not */
	@Override
	public boolean verifyEmail(User user) {
		String userEmail = user.getEmail();
		if (userRepository.findByEmail(userEmail) != null) {
			Optional<User> optionalUser = userRepository.findByEmail(userEmail);
			user = optionalUser.get();
			String userPassword = user.getPassword();
			Utility.sendMail(userEmail, userPassword, adminMailId, adminPassword);
			return true;
		}
		return false;
	}

	/**
	 * To Save user information in database
	 */
	@Override
	public void saveUser(User user) {
		userRepository.insert(user);
	}

	public boolean sendAuthenticationMail(User user) {
		// generate token
		String validToken = token.generator(user);
		logger.info("Your token is : " + validToken);
		token.parseJWT(validToken);
		// send mail with token
		String userEmail = user.getEmail();
		if (Utility.sendMailForActivation(userEmail, adminMailId, adminPassword, validToken)) {
			return true;
		} else
			return false;
	}
}
