package com.bridgelabz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

/**
 * purpose-To store user data into the database.DAO(Data Access Object) is the
 * media between service and database
 * 
 * @author yuga
 * @since 08-07-2018
 */
@Repository
public class UserDAOImplementation implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImplementation.class);

	/**
	 * To establish connections
	 */
	@Override
	public Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/Spring_Hibernate_Integration?useSSL=false", "root", "root");
		} catch (SQLException e) {
			logger.info("Error in establishing connection");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * To close all costly resourses
	 */
	@Override
	public void closeConnection(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/** To check person details by user name */
	@Override
	public User getUserByUserName(String uname) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();

		String query = "select * from User where UserName=?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, uname);
		ResultSet resultSet = preparedStatement.executeQuery();
		User user = null;
		if (resultSet.next()) {
			user = new User();
			user.setUserName(resultSet.getString(2));
			user.setPassword(resultSet.getString(3));
		}
		closeConnection(resultSet, preparedStatement, connection);
		return user;
	}

	/**
	 * To save person details into the database
	 */
	@Override
	public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
		Connection connection = getConnection();
		ResultSet resultSet = null;
		String query = "insert into User values(?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, user.getUserName());
		preparedStatement.setString(3, user.getPassword());
		preparedStatement.setString(4, user.getFirstName());
		preparedStatement.setString(5, user.getLastName());
		preparedStatement.setString(6, user.getMobNumber());
		preparedStatement.setString(7, user.getEmail());
		int count = preparedStatement.executeUpdate();
		closeConnection(resultSet, preparedStatement, connection);
		if (count == 7) {
			return true;
		}
		return false;
	}

	/**
	 * To fetch person details using his email id
	 */
	@Override
	public boolean getUserByUserEmail(String email) throws SQLException, ClassNotFoundException {
		System.out.println(email);
		Connection connection = getConnection();
		boolean found = false;
		String query = "select * from User where Email=?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, email);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			found = true;
		}
		closeConnection(resultSet, preparedStatement, connection);
		System.out.println(found);
		return found;
	}

}
