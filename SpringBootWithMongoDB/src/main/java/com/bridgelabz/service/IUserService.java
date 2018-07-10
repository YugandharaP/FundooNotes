package com.bridgelabz.service;

import java.util.Optional;

import com.bridgelabz.model.User;

/**
 * @author yuga
 *
 */
public interface IUserService {
	public boolean verifyEmail(User user);

	public void saveUser(User user);

	Optional<User> verifyUserByuserName(User user);
}
