package com.learn.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// https://www.baeldung.com/spring-security-authentication-with-a-database
@Service
public class CustomUserService implements UserDetailsService {
 
     @Autowired
     private UserDaoRepository userDaoRepository;
     
    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
    	 //Write your DB call code to get the user details from DB,But I am just hard coding the user
    	System.out.println("************ load User by username called for username:"+username);
       
        return userDaoRepository.getUser(username);
    	
        //return userDao.loadUserByUsername(username);
    }
    public void saveUser(CustomUser user){
    	userDaoRepository.saveUser(user);
    }
 
}

