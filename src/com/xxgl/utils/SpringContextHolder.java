package com.xxgl.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service

public class SpringContextHolder implements ApplicationContextAware {  
	  
	    private static ApplicationContext context;  
	  
	    @Override  
	    public void setApplicationContext(ApplicationContext context) throws BeansException {  
	    	SpringContextHolder.context = context;  
	    }  
	  
	     
	    public static Object getSpringBean(String beanName) {  
	       // notEmpty(beanName, "bean name is required");  
	        return context==null?null:context.getBean(beanName);  
	    }  
	  
	    public static String[] getBeanDefinitionNames() {  
	        return context.getBeanDefinitionNames();  
	    }  
}   