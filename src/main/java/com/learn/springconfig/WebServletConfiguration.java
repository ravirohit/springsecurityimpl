package com.learn.springconfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/*
 * Note: in the applcation, either of the file(WebServletConfiguration.java or SpringMvcInitializer.java) will fulfill the purpose of web.xml file. 
 *       So any one at a time can be enabled and it will work fine. 
 *       This class is disabled because SpringMvcInitializer.java file is enabled if we want to enable this file then disabled the file 
 *       SpringMvcInitializer.java first*/


// Replacing Web.xml
/*public class WebServletConfiguration implements WebApplicationInitializer{
	
	
    public void onStartup(ServletContext ctx) throws ServletException {
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        //webCtx.register(SpringConfig.class);   
        webCtx.register(new Class[]{SecurityConfig.class,DbConfiguration.class}); //ApplicationContextConfig.class
        webCtx.setServletContext(ctx);
        ServletRegistration.Dynamic servlet = ctx.addServlet("dispatcher", new DispatcherServlet(webCtx));
        servlet.setLoadOnStartup(1);
       // servlet.addMapping("/");
        servlet.addMapping("/api/*");
        //servlet.addMapping("/");
        ctx.addFilter("delegatingFilterProxy", DelegatingFilterProxy.class).addMappingForUrlPatterns(null, false, "dispatcher");
    }
}*/
