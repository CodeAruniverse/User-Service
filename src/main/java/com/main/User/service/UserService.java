package com.main.User.service;

import java.util.List;

import com.main.User.entities.User;

public interface UserService {

	//Create a User
//	User saveUser(List<User> user);
	
	//get all User
	List<User> getAllUser();
	
	//get User by userId
	
	User getUserById(String userId);

	List<User> saveUsers(List<User> users);
}
