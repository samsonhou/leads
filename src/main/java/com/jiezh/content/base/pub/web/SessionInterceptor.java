package com.jiezh.content.base.pub.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.pub.author.UserLoginLog;
/**
 * session 
 * @author liangds
 *
 */
public class SessionInterceptor implements HandlerInterceptor{
	Log log=new Log(SessionInterceptor.class);
	@Autowired
	SessionRegistry sessionRegistry;
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//		log.info(request.getRequestURI());
		String index=request.getContextPath()+"/index.do";
		String login=request.getContextPath()+"/login.do";
		
		String requestUrl=request.getRequestURI();
		//过滤外部访问接口
		if(requestUrl!=null && requestUrl.startsWith(request.getContextPath()+"/exchange/")){
			return true;
		}
		HttpSession session = request.getSession(false);
		//首页  登陆 不处理
		if(index.equals(request.getRequestURI()) || login.equals(request.getRequestURI())){
			if(login.equals(request.getRequestURI()) && session!=null){
//				log.info(session.getId());
				//UserLoginLog.updateSession(session.getId());
			}
			return true;
		}
		//更新访问时间
		if(session!=null){
			UserLoginLog.updateLastRequest(session.getId());
		}
		SessionInformation info = this.sessionRegistry.getSessionInformation(session.getId());
		if (info != null) {
			if (info.isExpired()) {
				UserLoginLog.updateLogout(session.getId());
				response.sendRedirect(index);
				return false;
			}
		}else{
			UserLoginLog.updateLogout(session.getId());
			response.sendRedirect(index);
			return false;
		}
		return true;
	}
	
}
