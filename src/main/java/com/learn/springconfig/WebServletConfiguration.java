package com.learn.springconfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;


// Replacing Web.xml
/*public class WebServletConfiguration implements WebApplicationInitializer{
	
	
    public void onStartup(ServletContext ctx) throws ServletException {
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        //webCtx.register(SpringConfig.class);   
        webCtx.register(WebSecurityConfig.class);
        webCtx.setServletContext(ctx);
        ServletRegistration.Dynamic servlet = ctx.addServlet("dispatcher", new DispatcherServlet(webCtx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        ctx.addFilter("delegatingFilterProxy", DelegatingFilterProxy.class).addMappingForUrlPatterns(null, false, "dispatcher");
    }
}*/
