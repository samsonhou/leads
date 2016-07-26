package com.jiezh.content.base.pub.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.weixin.tools.WeixinTools;

/**
 * 异常处理
 * 
 * @author liangds
 *
 */
public class PlatformExceptionHandler implements HandlerExceptionResolver,Ordered {
	
	public ModelAndView resolveException(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse, Object paramObject, Exception paramException) {
		if(WeixinTools.isWeixin(paramHttpServletRequest)){
			ModelAndView mv=new ModelAndView("weixin/pub/error");
			mv.addObject("msg", "系统异常，请联系管理员！");
			return mv;
		}else{
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("errorMsg",paramException.getMessage());
			StringPrintWriter strintPrintWriter = new StringPrintWriter();
			paramException.printStackTrace(strintPrintWriter);
			model.put("stackTrace", strintPrintWriter.getString());
			return new ModelAndView("pub/500", model);
		}
		
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

}
