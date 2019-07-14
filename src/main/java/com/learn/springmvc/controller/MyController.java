package com.learn.springmvc.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.repository.CustomUserService;

@RestController
//@RequestMapping("/rest")
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
	
	@PostMapping(value="/save",produces="application/json")
	public void saveUser(HttpServletRequest request, HttpServletResponse response){
		String requestBody = null;
		try{
			requestBody = IOUtils.toString(request.getReader());
			System.out.println("save user resources get called, requestbody:"+requestBody);
			System.out.println(requestBody.toString());
			customUserService.saveUser(requestBody);
			response.sendRedirect("/springsecurityimpl/customLogin.html");
		}	
		catch(Exception e){
			System.out.println("Exception occur: "+e);
		}
	}
	
	@GetMapping(value= { "/welcome**" }, produces = "application/json")    
    public ZonedDateTime currentTime(){
        System.out.println("currentTime: ZoneDate time:"+ZonedDateTime.now());
        return ZonedDateTime.now();
    }
	@GetMapping(value="/admin**", produces = "application/json")
	public ZonedDateTime secretTime() {
		System.out.println("secretTime: ZonedDateTime.now(ZoneId.of(\"UTC\")): "+ZonedDateTime.now(ZoneId.of("UTC")));
		return  ZonedDateTime.now(ZoneId.of("UTC"));
	}
	@GetMapping(value="/dba**", produces = "application/json")
	public String dbaPage() {
		System.out.println("ZonedDateTime.now(ZoneId.of(\"UTC\")): "+ZonedDateTime.now(ZoneId.of("UTC")));
		return  "dbapage method called";
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
}
