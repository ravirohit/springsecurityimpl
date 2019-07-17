package com.learn.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// https://www.baeldung.com/spring-security-authentication-with-a-database
@Service
public class CustomUserService implements UserDetailsService {
 
     @Autowired
     private UserDaoRepository userDaoRepository;
     private final ObjectMapper objectMapper = new ObjectMapper();
     
    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
    	 //Write your DB call code to get the user details from DB,But I am just hard coding the user
    	System.out.println("************ load User by username called for username:"+username);
       
    	
    	
        return userDaoRepository.getUser(username);
    	
        //return userDao.loadUserByUsername(username);
    }
    public void saveUser(String  requestBody) throws Exception{
    	 CustomUser user = null;
    	 String uname=null;
    	 String pwd = null;
    	 if(requestBody.toString().indexOf('{') >= 0 ) {
    		 user = objectMapper.readValue(requestBody, CustomUser.class);  
          }
          else{
        	user = new CustomUser();
          	uname = requestBody.substring(requestBody.indexOf("=")+1, requestBody.indexOf("&"));
          	pwd = requestBody.substring(requestBody.lastIndexOf("=")+1);
          	user.setUname(uname);
          	user.setPwd(pwd);
          }
    	userDaoRepository.saveUser(user);
    }
 
}

