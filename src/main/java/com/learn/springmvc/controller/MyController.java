package com.learn.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springsecurity.config.CustomUserService;

@RestController
// http://localhost:8080/springsecurityimpl/customLogin.html
public class MyController {
	//@ResponseBody
	// @RequestMapping(value = "/{name}.xml", method = RequestMethod.GET, produces = "application/xml")
	/*@GetMapping(value="/login", produces = "application/json")
	public String login() {
		System.out.println("loging page method called");
		return  "login done";
	}*/
	@Autowired
	CustomUserService customUserService;
	@Autowired
    private HttpServletRequest request;
	
	@PostMapping(value="/register",produces="application/json")
	public void saveUser(HttpServletRequest request, HttpServletResponse response){  // in request we can use LoginRequest object ref also which will be mapped from request
		String requestBody = null;
		try{
			requestBody = IOUtils.toString(request.getReader());   // uname=rohit&pwd=rohit&role=USER
			System.out.println("save user resources get called, requestbody:"+requestBody);
			System.out.println(requestBody.toString());
			customUserService.registerUser(requestBody);
			response.sendRedirect("/springsecurityimpl/customLogin.html");
		}	
		catch(Exception e){
			System.out.println("Exception occur: "+e);
		}
	}
	@GetMapping(value="/login")
	public void loginRedirect(HttpServletResponse response){
		try {
			System.out.println("login method get called");
			response.sendRedirect("/springsecurityimpl/customLogin.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/logout", method=RequestMethod.GET)  
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		System.out.println("----- logout api getcalled: -----------");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         //return "redirect:/";
        response.sendRedirect("/springsecurityimpl/customLogin.html");
     }  
	@GetMapping(value= { "/public" }, produces = "application/json")    
    public String publicSite(){
        System.out.println("currentTime: ZoneDate time:"+ZonedDateTime.now());
        return "Thank you for visiting our site! :)";
    }
	@GetMapping(value= { "/allinternalusers" }, produces = "application/json")     // http://localhost:8080/springsecurityimpl/api/allinternalusers
    public String currentTime(){
        System.out.println("currentTime: ZoneDate time:"+ZonedDateTime.now());
        return "------- Welcome to Employee Help Portal --------";
    }
	@GetMapping(value="/admin", produces = "application/json")
	public ZonedDateTime secretTime() {
		System.out.println("secretTime: ZonedDateTime.now(ZoneId.of(\"UTC\")): "+ZonedDateTime.now(ZoneId.of("UTC")));
		return  ZonedDateTime.now(ZoneId.of("UTC"));
	}
	@GetMapping(value="/dba", produces = "application/json")
	public String dbaPage() {
		System.out.println("ZonedDateTime.now(ZoneId.of(\"UTC\")): "+ZonedDateTime.now(ZoneId.of("UTC")));
		return  "dbapage method called";
	}

}
