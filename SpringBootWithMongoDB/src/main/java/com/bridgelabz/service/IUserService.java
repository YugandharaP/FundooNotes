package com.bridgelabz.service;

import java.util.Optional;

import com.bridgelabz.model.User;

/**
 * 
 * @author yuga
 * @since 09/07/2018
 *        <p>
 * 		<b>To connect the controller and mongoDb repository.service interface
 *        is the media between controller and repository </b>
 *        </p>
 */
public interface IUserService {
	/** To verify email which is present in database or not */
	public boolean verifyEmail(User user);

	/**
	 * To Save user information in database
	 */
	public void saveUser(User user);

	/**
	 * @param user
	 * @return boolean values
	 * <p>To verify user which is present in database or not </p>
	 */
	boolean verifyUser(User user);

	/**
	 * To verify userName is present in database or not
	 */
	Optional<User> findByUserName(User user);
	/**
	 * @param email
	 * <p>
	 * 	To Set user status into the database
	 * </p>
	 * @return 
	 */
	public boolean setActivationStatus(String email);
}
