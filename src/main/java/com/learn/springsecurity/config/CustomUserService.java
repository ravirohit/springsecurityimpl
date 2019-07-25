package com.learn.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.model.LoginRequest;
import com.learn.repository.UserDaoRepository;


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
    public void registerUser(String  requestBody) throws Exception{
    	 CustomUser user = new CustomUser();
    	 String uname=null;
    	 String pwd = null;
    	 String roleVal = null;
    	 Role role = new Role();
    	 if(requestBody.toString().indexOf('{') >= 0 ) {
    		 LoginRequest loginreq = objectMapper.readValue(requestBody, LoginRequest.class);
    		 user.setUname(loginreq.getUname());
    		 user.setPwd(loginreq.getPwd());
    		 role.setName(loginreq.getRole());
    		 user.getAuthorities().add(role);
    		 
          }
          else{
          	uname = requestBody.substring(requestBody.indexOf("=")+1, requestBody.indexOf("&"));
          	requestBody=requestBody.substring(requestBody.indexOf("&")+1);
          	System.out.println("requestbody:"+requestBody);
          	pwd = requestBody.substring(requestBody.indexOf("=")+1, requestBody.indexOf("&"));
          	System.out.println("requestbody:"+pwd);
          	roleVal = requestBody.substring(requestBody.lastIndexOf("=") + 1);
          	System.out.println("requestbody:"+roleVal);
          	user.setUname(uname);
          	user.setPwd(pwd);
	   		role.setName(roleVal);
	   		user.getAuthorities().add(role);
          }
    	userDaoRepository.saveUser(user);
    }
 
}


