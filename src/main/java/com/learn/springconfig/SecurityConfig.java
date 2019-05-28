package com.learn.springconfig;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc                                // <mvc:annotation-driven/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.learn")   // <context:component-scan/>
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// Below way of creating user role create exception:
	// EXCEPTION: Spring Security – There is no PasswordEncoder mapped for the id “null”   for spring < 5.0
	  /*auth.inMemoryAuthentication().withUser("mkyong").password("123456").roles("USER");
	  auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
	  auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");*/
	  // Solution for the above prblm... create like below:
		auth.inMemoryAuthentication().withUser("mkyong").password("{noop}123456").roles("USER");
		  auth.inMemoryAuthentication().withUser("admin").password("{noop}123456").roles("ADMIN");
		  auth.inMemoryAuthentication().withUser("dba").password("{noop}123456").roles("DBA");
	  
	  
	  
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


	  http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
		.and().formLogin();
		
	}
	/*private void loginSuccessHandler(
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
	*/
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
		System.out.println("######### SecurityConfiguration: configure");
	  
	  
	}
	*/
	 
	/*@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = null;
		System.out.println("######### SecurityConfiguration: authenticationProvider");
	    return daoAuthenticationProvider;
	}
	 */
	/*@Bean
	public PasswordEncoder encoder() {
		System.out.println("######### SecurityConfiguration: encoder");
	    return new BCryptPasswordEncoder(11);
	}*/
 
}
