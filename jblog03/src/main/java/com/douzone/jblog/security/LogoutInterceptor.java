package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LogoutInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("authUser");
		session.invalidate();

		if (request.getParameter("blogid") != null) {
			response.sendRedirect(request.getContextPath() + "/" + request.getParameter("blogid"));
			return false;
		}

		response.sendRedirect(request.getContextPath());
		return false;
	}
}