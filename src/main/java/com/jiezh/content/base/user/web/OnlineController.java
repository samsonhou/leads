package com.jiezh.content.base.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.user.service.imp.OnlineUserServiceImp;
/**
 * 在线用户
 * @author liangds
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/base/user/online/")
public class OnlineController extends WebAction{
	@Autowired
	OnlineUserServiceImp onlineUserService;
	
	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv=new ModelAndView("base/user/online");
		mv.addObject("userList",onlineUserService.getAllUser());
		return mv ;
	}
	/*
	 * 剔除用户
	 */
	@RequestMapping("reject")
	public ModelAndView reject(String userCode,String sessionId) throws Exception {
		onlineUserService.reject(userCode, sessionId);
		return new ModelAndView("redirect:index.do");
	} 
}
