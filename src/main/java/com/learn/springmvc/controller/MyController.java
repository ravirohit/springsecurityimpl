package com.learn.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	//@ResponseBody
	// @RequestMapping(value = "/{name}.xml", method = RequestMethod.GET, produces = "application/xml")
	@RequestMapping(path= "/greet/{name}",method=RequestMethod.GET)    
    public String greet(@PathVariable String name, ModelMap model){
        String greet =" Hello !!!" + name + " How are You?";
        model.addAttribute("greet", greet);
        System.out.println(greet);
        
        return "greet";
    }
	@RequestMapping(path="/login", method = RequestMethod.GET)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("username : "+username+"   password:"+password);
		return "login method get called";
	}
	
}
