package com.bridgelabz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.model.User;
import com.bridgelabz.repository.IUserRepository;

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

	/**
	 * To verify userName is present in database or not
	 */
	@Override
	public Optional<User> verifyUserByuserName(User user) {
		String details = user.toString();
		return userRepository.findById(user.getUserName());
	}

	/** To verify user is present in database or not */
	@Override
	public boolean verifyEmail(User user) {
		if (userRepository.existsById(user.getUserName())) {
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

}
