   package com.bridgelabz.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.UserDAO;
import com.bridgelabz.model.User;

/**purpose-To implents User Service interface and provide all implentation for abstract methods
 * @author yuga
 *@since 8-07-2018
 */
@Service
public class UserServiceImplementation implements UserService{
	@Autowired
	UserDAO userRepository;

	/**To verify user is present in database or not
	 */
	@Override
	public int verifyUser(User user) throws ClassNotFoundException, SQLException {
		User userReturn = userRepository.getUserByUserName(user.getUserName());
		if (userReturn != null) {
			if (userReturn.getPassword().equals(user.getPassword())) {
				System.out.println("pass match");
				return 1;
			} else {
				return 0;
			}
		}
		return -1;

	}

	/**To verify email is present in database or not	 */
	@Override
	public  boolean verifyEmail(User user) throws ClassNotFoundException, SQLException {
		if(userRepository.getUserByUserEmail(user.getEmail())) {
			return true;
		}
		return false;
		}

	/**To Save user information in database
	 */
	@Override
	public void saveUser(User user) throws ClassNotFoundException, SQLException {
		userRepository.saveUser(user);
		
	}
}
