package com.learn.springconfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * Note: in the application, either of the file(WebServletConfiguration.java or SpringMvcInitializer.java) will fulfill the purpose of web.xml file. 
 *       So any one at a time can be enabled and it will work fine. 
 *       This class is enabled because WebServletConfiguration.java file is disabled if we want to disabled this file then enabled the file 
 *       WebServletConfiguration.java first*/

public class SpringMvcInitializer 
extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class };//, DbConfiguration.class}; //,ApplicationContextConfig.class 
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/api/*"};
	}

}
