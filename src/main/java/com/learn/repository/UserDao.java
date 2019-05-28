package com.learn.repository;

import java.util.List;

public interface UserDao {
	   void add(User user);
	   List<User> listUsers();
}
