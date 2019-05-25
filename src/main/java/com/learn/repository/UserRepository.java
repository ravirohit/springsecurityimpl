package com.learn.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	 
    public User findByUsername(String username){
    	
    	System.out.println("findByUser Method called");
    	return new User();
    }
}

