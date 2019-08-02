package com.learn.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.learn.springsecurity.config.CustomAccessDeniedHandler;
import com.learn.springsecurity.config.CustomAuthenticationProvider;
import com.learn.springsecurity.config.CustomUsernamePasswordAuthenticationFilter;



@Import({DbConfiguration.class})
@Configuration
@EnableWebMvc                                // <mvc:annotation-driven/
//@EnableTransactionManagement
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.learn")   // <context:component-scan/>
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	   // auth.authenticationProvider(authenticationProvider());
		 auth.authenticationProvider(getAuthenticationProvider());
	}
	 
	@Bean
	public CustomAuthenticationProvider getAuthenticationProvider(){
		return new CustomAuthenticationProvider();
	}
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder(11);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	    .antMatchers("/api/public","/api/uploadFile","api/register","/api/login","/api/logout").permitAll()  // api will be accessible without authentication
	    .antMatchers("/api/allinternalusers","/userhomepage.html").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
	    .antMatchers("/api/dba","/dbapage.html").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')") // accessible if use is having either role.
		.antMatchers("/api/admin","/adminpage.html").access("hasRole('ROLE_ADMIN')")    // api will be accessible if user is having access and this role 
		.and().formLogin()//.failureHandler(customAuthenticationFailureHandler())  not need as logic written in CustomUsernamePasswordAuthenticationFilter class
		.loginPage("/api/login")
		.permitAll()     // if user has not logged in and trying to access secure resource, then Spring will automatically redirect to login page "/login"
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // once we call the /logout, Spring will invalidate the session and 
		.invalidateHttpSession(true)                                              // by default redirect to the login page.
	    .and()
	    .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
	    .csrf().disable();  // Disables CSRF protection
	  http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		
	}
	@Bean
    public CustomUsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
		CustomUsernamePasswordAuthenticationFilter authenticationFilter
            = new CustomUsernamePasswordAuthenticationFilter();
       // authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        // authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }
	@Bean(name = "multipartResolver")    // will be used when we are using file upload concept.
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(100000);    // if we will try to upload file whose size is greater than this. it will throw exception.
	    return multipartResolver;
	}
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	
	/*@Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }*/
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
	/*@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}*/
 
}
