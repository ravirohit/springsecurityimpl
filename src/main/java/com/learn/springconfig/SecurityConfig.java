package com.learn.springconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc                                // <mvc:annotation-driven/
//@EnableTransactionManagement
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
	    .antMatchers("/api/welcome**","/api/login").permitAll()  // api will be accessible without authentication
		.antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")    // api will be accessible if user is having access and this role 
		.antMatchers("/api/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')") // accessible if use is having either role.
		.and().formLogin().loginPage("/api/login")
		.permitAll(true)     // if use has not logged in and trying to access secure resource, then Spring will automatically redirect to login page "/login"
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // once we call the /logout, Spring will invalidate the session and 
		.invalidateHttpSession(true)                                              // by default redirect to the login page.
	    .and()
	    .csrf().disable();  // Disables CSRF protection
		
	}
	/*@Bean
	WebMvcConfigurer myWebMvcConfigurer() {
	      return new WebMvcConfigurerAdapter() {
	          @Override
	          public void addViewControllers(ViewControllerRegistry registry) {
	              ViewControllerRegistration r = registry.addViewController("/login");
	              r.setViewName("customLogin.html");
	          }
	      };
	  }*/
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
