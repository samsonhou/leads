package com.jiezh.content.base.logout;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
/**
 * 自定义退出
 * @author liangds
 *
 */
public class CustomLogout extends LogoutFilter{

	public CustomLogout(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers);
	}

	public CustomLogout(LogoutSuccessHandler logoutSuccessHandler,LogoutHandler[] handlers) {
		super(logoutSuccessHandler, handlers);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		request.setAttribute("delSessionId", request.getSession().getId());
		super.doFilter(req, res, chain);
	}

}
