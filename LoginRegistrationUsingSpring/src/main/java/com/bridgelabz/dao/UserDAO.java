package com.bridgelabz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bridgelabz.model.User;

/**
 * purpose-To connect database and do the various operations.this interface is
 * the media between controller and database
 * 
 * @author yuga
 *@since 08/07/2018
 */
public interface UserDAO {
	/**
	 * Method to establish and return the connection
	 * 
	 * @return connection JDBC Connection
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws ClassNotFoundException;

	/**
	 * Method to close all the JDBC connection
	 * 
	 * @param resultSet
	 *            ResultSet
	 * @param preparedStatement
	 *            PreparedStatement
	 * @param connection
	 *            Connection
	 */
	public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection);

	/**
	 * Method to verify whether a user is present in database or not
	 * 
	 * @param uname
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public User getUserByUserName(String uname) throws ClassNotFoundException, SQLException;

	/**
	 * Function to save user in the database
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean saveUser(User user) throws SQLException, ClassNotFoundException;

	/**
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean getUserByUserEmail(String email) throws SQLException, ClassNotFoundException;
}
