package com.learn.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// replacement of SpringApplicationContext.xml file
@EnableWebMvc                                // <mvc:annotation-driven/
@ComponentScan(basePackages = "com.learn")   // <context:component-scan/>
public class SpringConfig extends WebMvcConfigurerAdapter{
   
	// below method will not be in picture if we will use Rest MVC architecture.
    @Bean
    public ViewResolver viewResolver() {
    	//@Configuration 
    	System.out.println("@@@@@ SpringConfig: configureDefaultServletHandling viewResolver@@@@@@@@");
    	InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    	System.out.println("@@@@@ SpringConfig: configureDefaultServletHandling @@@@@@@@");
        configurer.enable();
    }
}
