package com.learn.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.model.UploadFile;
import com.learn.model.UploadFileEntity;
import com.learn.model.UploadJsonFile;
import com.learn.repository.CustomUserService;
import com.learn.service.FileOperationService;

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
	@Autowired
	FileOperationService fileOperationService;
	
	@Autowired
    private HttpServletRequest request;
	
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
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)  // this is FORM submit from client only FILE data
	public String submit(@RequestParam("file") MultipartFile file) {
		System.out.println("upload file resource get called:"+file+"    size: "+file.getSize());
		if (!file.isEmpty()) {
            try {
                String uploadsDir = "/uploads/";
                String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
                System.out.println("path where file will be uploaded:"+realPathtoUploads);
                if(! new File(realPathtoUploads).exists())
                {
                    new File(realPathtoUploads).mkdir();
                }
                String orgName = file.getOriginalFilename();
                String filePath = realPathtoUploads + orgName;
                File dest = new File(filePath);
                file.transferTo(dest);
            }catch(Exception e){
            	System.out.println("Exception occur while uploading file:"+e);
            	return "Server internal Error. Please try after sometimes!";
            }
		}
	    return "File uploaded Successfully";
	}
	@RequestMapping(value = "/uploadFilewithinfo", method = RequestMethod.POST)  // this is FORM submit from client File and other info
	public String submit(@ModelAttribute UploadFile uploadFile) {
		System.out.println(" uploadFile resource get called:"+uploadFile.getName()+"  email:"+uploadFile.getEmail()+" file:"+uploadFile.getFile());
		fileOperationService.storeFile(uploadFile);
		
		
	    return "File uploaded Successfully";
	}
	@GetMapping("/downloadFileWithInfo/{email}")  // return response in JSON
	public UploadFileEntity downloadFile(@PathVariable("email") String email){
		System.out.println("download file for email:"+email);
		return fileOperationService.getFile(email);
	}
	//@PostMapping("/uploadjsonfile")
	@RequestMapping(value = "/uploadjsonfile", consumes = "application/json", method = RequestMethod.POST)
	public String uploadjsonfile(@RequestBody UploadJsonFile uploadJsonFile){
		System.out.println("upload file:"+uploadJsonFile);
		fileOperationService.storeJsonFile(uploadJsonFile);
		return "json file request uploaded successfully";
	}
	@GetMapping("/downloadjsonfilewithinfo/{email}")  // return response in JSON
	public UploadJsonFile downloadJsonFile(@PathVariable("email") String email){
		System.out.println("download jsonfile for email:"+email);
		return fileOperationService.getJsonFile(email);
	}
	
}
