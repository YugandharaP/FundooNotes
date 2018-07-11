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
	public boolean verifyEmail(User user);

	public void saveUser(User user);

	Optional<User> verifyUserByuserName(User user);
}
