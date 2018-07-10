package com.bridgelabz.service;

import java.sql.SQLException;

import com.bridgelabz.model.User;

/**
 * purpose-It is an interface to connect controller and model
 * 
 * @author yuga
 * @since 08-07-2018
 */
public interface UserService {
	public int verifyUser(User user) throws ClassNotFoundException, SQLException;

	public boolean verifyEmail(User user) throws ClassNotFoundException, SQLException;

	public void saveUser(User user) throws ClassNotFoundException, SQLException;
}
