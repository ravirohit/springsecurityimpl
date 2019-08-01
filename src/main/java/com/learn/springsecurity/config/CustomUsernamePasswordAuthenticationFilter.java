package com.learn.springsecurity.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.model.LoginRequest;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
    private CustomUserService userService;
	
	public CustomUsernamePasswordAuthenticationFilter(){
		
	}
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;
        System.out.println("======================== Rohit custom authentication token =================");
        try {
            requestBody = IOUtils.toString(request.getReader());  // request coming from client using form method will have the parameter value as string will not be in json format
            System.out.println("---- payload String for login api:"+requestBody.toString()); 
            LoginRequest authRequest = null;
            String uname = null, pwd = null;
            UsernamePasswordAuthenticationToken token = null;
            if(requestBody.toString().indexOf('{') >= 0 ) {
               authRequest = objectMapper.readValue(requestBody, LoginRequest.class);  
               uname = authRequest.getUname();    
               pwd = authRequest.getPwd();
               token = new UsernamePasswordAuthenticationToken(authRequest.getUname(), authRequest.getPwd());
            }
            else{
            	uname = requestBody.substring(requestBody.indexOf("=")+1, requestBody.indexOf("&"));
            	pwd = requestBody.substring(requestBody.lastIndexOf("=")+1);
            }
            
            
            // for form submit request will throw error.
                                       // while mapper will work fine if request is sent from postman with credential as payload.
           /*Value of requestBody for form submit request: 
            *   requestBody:  
            *                 uname=admin&pwd=password
            * above way of consuming request will throw error in objectMapper statement while will work fine for below request type.
            * while for postman request with payload is:
            *   requestBody:	{
									"uname":"admin",
									"pwd":"password"
								 }
            * */
            /* if  we will not customize the userNamePasswordAuthenticationToken then Spring consider the incoming request is POST and two parameter
             * is username and password. and if we are having other name than that then we have to customize it and create our onw authentication
             * object as done here  */
            
            //////// validating request before creating object:
            CustomUser user = userService.loadUserByUsername(uname);
            if (user == null || (!user.getUsername().equalsIgnoreCase(uname)) || (!pwd.equals(user.getPassword()))) {
            	response.sendRedirect("/springsecurityimpl/onFailureCustomLogin.html");
            }
            token = new UsernamePasswordAuthenticationToken(uname, pwd);
            // Allow subclasses to set the "details" property
            setDetails(request, token);
 
            return this.getAuthenticationManager().authenticate(token);
        } catch(IOException e) {
            System.out.println("exception occur in custom authentication token:"+e);
            throw new InternalAuthenticationServiceException("Internal server error because of Authentication Token error", e);
        }
    }
}
