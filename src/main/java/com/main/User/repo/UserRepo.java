package com.main.User.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.User.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

}
