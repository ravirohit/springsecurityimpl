package com.learn.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDaoRepository {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	
	public void saveUser(CustomUser user1){
		System.out.println("saving user to the db");
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
		Long id = (Long) sessionFactory.getCurrentSession().save(user);
		System.out.println("user info persisted with id:"+id);
	}
	public CustomUser getUser(String name){
		System.out.println("getting user to the db");
		//Long id = (Long) sessionFactory.getCurrentSession().load(user);
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CustomUser.class);
		CustomUser user = (CustomUser) criteria.add(Restrictions.eq("username", "admin"))
		                             .uniqueResult();
		System.out.println("user got:"+user);
		return user;
	}
	

}
