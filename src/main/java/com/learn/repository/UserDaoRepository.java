package com.learn.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.learn.springsecurity.config.CustomUser;
import com.learn.springsecurity.config.Role;

@Repository
@Transactional
public class UserDaoRepository {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	public void saveUser(CustomUser user){
		System.out.println("saving user to the db");
		//CustomUser user = new CustomUser();
        user.setFirstName("fname");
        user.setLastName("lname");
		Long id = (Long) sessionFactory.getCurrentSession().save(user);
		System.out.println("user info persisted with id:"+id);
	}
	public CustomUser getUser(String name){
		System.out.println("getting user to the db");
		//Long id = (Long) sessionFactory.getCurrentSession().load(user);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CustomUser.class);
		CustomUser user = (CustomUser) criteria.add(Restrictions.eq("uname",name))
		                             .uniqueResult();
		System.out.println("user got:"+user);
		return user;
	}
	

}
