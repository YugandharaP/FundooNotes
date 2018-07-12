package com.bridgelabz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.bridgelabz.model.User;

/**
 * purpose-To deal with MongoDB database
 * 
 * @author yuga
 *
 */
@Repository
public interface IUserRepository extends MongoRepository<User, String> {
	/**
	 * used to find user in database is allready present or not through email id
	 * 
	 * @param email
	 * @return
	 */
	public Optional<User> findByEmail(String email);

}
