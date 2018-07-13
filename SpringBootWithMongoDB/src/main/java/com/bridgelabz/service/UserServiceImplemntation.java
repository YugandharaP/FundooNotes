package com.bridgelabz.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.model.User;
import com.bridgelabz.repository.IUserRepository;
import com.bridgelabz.security.JwtTokenProvider;
import com.bridgelabz.util.MailService;
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
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplemntation.class);

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
			sendAuthenticationMail(user);
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
			MailService.sendMail(userEmail, userPassword);
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

	/**
	 * To send mail with activation link
	 * 
	 * @param user
	 * @return boolean value
	 */
	public boolean sendAuthenticationMail(User user) {
		// generate token
		String validToken = token.generator(user);
		logger.info("Your token is : " + validToken);
		//token.parseJWT(validToken);
		// send mail with token
		String userEmail = user.getEmail();
		if (MailService.sendMailForActivation(userEmail, validToken)) {
			return true;
		} else
			return false;
	}

	/**
	 * To set user activation status into database
	 * 
	 * @param email
	 */
	@Override
	public boolean setActivationStatus(String email) {
			Optional<User> optionalUser = userRepository.findByEmail(email);
			User user = new User();
			optionalUser.get().setEmail(optionalUser.get().getEmail());
			optionalUser.get().setPassword(optionalUser.get().getPassword());
			optionalUser.get().setFirstName(optionalUser.get().getFirstName());
			optionalUser.get().setLastName(optionalUser.get().getLastName());
			optionalUser.get().setMobile(optionalUser.get().getMobile());
			optionalUser.get().setStatus("true");
			user = optionalUser.get();
			saveUser(user);
			return true;
		}
}
