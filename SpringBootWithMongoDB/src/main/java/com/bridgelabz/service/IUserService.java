package com.bridgelabz.service;

import java.util.Optional;

import com.bridgelabz.model.User;

/**
 * purpose- To connect the controller and mongoDb repository.service interface
 * is the media between controller and repository
 * 
 * @author yuga
 * @since 09/07/2018
 */
public interface IUserService {
	/** To verify email which is present in database or not */
	public boolean verifyEmail(User user);

	/**
	 * To Save user information in database
	 */
	public void saveUser(User user);

	/** To verify user which is present in database or not */
	boolean verifyUser(User user);

	/**
	 * To verify userName is present in database or not
	 */
	Optional<User> findByUserName(User user);
}
