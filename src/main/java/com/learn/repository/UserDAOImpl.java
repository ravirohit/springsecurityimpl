package com.learn.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl {
   
     public CustomUser loadUserByUsername(final String username) {
         //Write your DB call code to get the user details from DB,But I am just hard coding the user
            CustomUser user = new CustomUser();
            user.setFirstName("admin");
            user.setLastName("kumar");
            user.setUsername("admin");
            user.setPassword("password");
            Role r = new Role();
            r.setName("ROLE_ADMIN");
            List<Role> roles = new ArrayList<Role>();
            roles.add(r);
            user.setAuthorities(roles);
            return user;
        }
 
}

