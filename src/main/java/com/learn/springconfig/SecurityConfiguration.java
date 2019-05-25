package com.learn.springconfig;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.learn.repository.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	protected void configure(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable() //We don't need CSRF for this example
	            .authorizeRequests()
	            .anyRequest().authenticated() // all request requires a logged in user
	 
	            .and()
	            .formLogin()
	            .loginProcessingUrl("/login") //the URL on which the clients should post the login information
	            .usernameParameter("login") //the username parameter in the queryString, default is 'username'
	            .passwordParameter("password") //the password parameter in the queryString, default is 'password'
	            .successHandler(this::loginSuccessHandler)
	            .failureHandler(this::loginFailureHandler)
	 
	            .and()
	            .logout()
	            .logoutUrl("/logout") //the URL on which the clients should post if they want to logout
	            .logoutSuccessHandler(this::logoutSuccessHandler)
	            .invalidateHttpSession(true)
	 
	            .and()
	            .exceptionHandling(); //default response if the client wants to get a resource unauthorized
	           // .authenticationEntryPoint(new BasicAuthenticationEntryPoint("401"));
	    }
	 	private void loginSuccessHandler(
		        HttpServletRequest request,
		        HttpServletResponse response,
		        Authentication authentication) throws IOException {
		 
		        response.setStatus(HttpStatus.OK.value());
		        System.out.println("login successfully");
		        //objectMapper.writeValue(response.getWriter(), "Yayy you logged in!");
		    }
		 
		    private void loginFailureHandler(
		        HttpServletRequest request,
		        HttpServletResponse response,
		        AuthenticationException e) throws IOException {
		 
		        response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        System.out.println("not authorized to login");
		        //objectMapper.writeValue(response.getWriter(), "Nopity nop!");
		    }
		    private void logoutSuccessHandler(
		            HttpServletRequest request,
		            HttpServletResponse response,
		            Authentication authentication) throws IOException {
		     
		            response.setStatus(HttpStatus.OK.value());
		          //  objectMapper.writeValue(response.getWriter(), "Bye!");
		            System.out.println("logout handler called");
		        }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
		System.out.println("######### SecurityConfiguration: configure");
	    auth.authenticationProvider(authenticationProvider());
	}
	 
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		System.out.println("######### SecurityConfiguration: authenticationProvider");
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	 
	@Bean
	public PasswordEncoder encoder() {
		System.out.println("######### SecurityConfiguration: encoder");
	    return new BCryptPasswordEncoder(11);
	}
 
}
