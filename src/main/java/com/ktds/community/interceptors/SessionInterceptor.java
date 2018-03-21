package com.ktds.community.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.community.constants.Member;



public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	//private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class); 
	private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {
		
		String contextPath = request.getContextPath();
		
		
		if (request.getSession().getAttribute(Member.USER) == null) {
			

			response.sendRedirect(contextPath + "/login");
			logger.info(request.getRequestURI() + "안돼, 돌아가");
			return false;

		}

		return true; // return trued일때만 controller로 false는 브라우저로 감 위에서는 로그인 페이지로
	}

	
}
