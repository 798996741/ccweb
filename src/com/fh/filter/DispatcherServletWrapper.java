package com.fh.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;


@SuppressWarnings("serial")
public class DispatcherServletWrapper extends DispatcherServlet {
	
	@Override
	protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		HandlerExecutionChain chain = super.getHandler(request);
		Object handler = chain.getHandler();
        if (!(handler instanceof HandlerMethod)) {
            return chain;
        }
		
        HandlerMethod hm = (HandlerMethod)handler;
        if (!hm.getBeanType().isAnnotationPresent(Controller.class)) {
        	return chain;
        }
        
        //本扩展仅处理@Controller注解的Bean
		return new HandlerExecutionChainWrapper(chain,request,getWebApplicationContext());
	}
	
}