package com.jiezh.content.base.pub.token;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.jiezh.content.base.pub.web.WebTools;

/**
 * token验证 
 * 防止重复提交
 * @author liangds
 * @date 205-12-22
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	private String TOKEN_NAME="_token_";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String repeat=request.getContextPath()+"/repeat.do";
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.generate();
				if (needSaveSession) {
					request.getSession(false).setAttribute(TOKEN_NAME,System.currentTimeMillis()+WebTools.getRandomChar(36));
				}
				boolean needRemoveSession = annotation.validate();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						response.sendRedirect(repeat);
						return false;
					}
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		boolean result=false;
		String clinetToken = request.getParameter(TOKEN_NAME);
		if(clinetToken!=null){
			String serverToken = (String) request.getSession(false).getAttribute(TOKEN_NAME);
			if(serverToken!=null){
				if(serverToken.equals(clinetToken)){
					request.getSession(false).removeAttribute(TOKEN_NAME);
					result=false;
				}else{
					//session中不存在该token，重复提交
					result=true;
				}
			}else{
				//如果客户端有token，服务器端还没初始化token,一律视为重复提交
				result=true;
			}
		}
		return result;
	}
}
