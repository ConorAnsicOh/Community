package com.ktds.community.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IpBlockInterceptor extends HandlerInterceptorAdapter {
	
	private List<String> blackList;
	
	public IpBlockInterceptor() {
		blackList = new ArrayList<String>();
		blackList.add("192.168.201.15");
		
		blackList.add("192.168.201.26");
		
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestIp = request.getRemoteAddr();
		
		if( blackList.contains(requestIp) ) {
			System.out.println("false");
			RequestDispatcher rd = 
						request.getRequestDispatcher("/WEB-INF/view/error/500.jsp");
			rd.forward(request, response);
			return false;
		}
		

		return true;
	}
	
	
}
