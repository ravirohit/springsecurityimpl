package com.learn.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// https://www.baeldung.com/spring-security-authentication-with-a-database
@Service
public class CustomUserService implements UserDetailsService {
 
     @Autowired
     private UserDAOImpl userDao;
     
     
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }
 
}

