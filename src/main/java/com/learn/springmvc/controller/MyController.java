package com.learn.springmvc.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
}
