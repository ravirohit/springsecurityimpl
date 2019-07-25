package com.learn.springsecurity.config;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    private String name;
     
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private CustomUser user;
    
    public CustomUser getUser() {
		return user;
	}

	public void setUser(CustomUser user) {
		this.user = user;
	}

	public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getAuthority() {
        return this.name;
    }
}
