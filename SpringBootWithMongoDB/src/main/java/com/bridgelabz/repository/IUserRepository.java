package com.bridgelabz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

/**
 * purpose-To deal with MongoDB database
 * 
 * @author yuga
 *
 */
@Repository
public interface IUserRepository extends MongoRepository<User, String> {

}
