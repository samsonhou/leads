
package com.jiezh.content.weixin.jieyi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.weixin.base.WeixinAction;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 微信 跟踪回复
 * @author liangds
 * @since  2016年2月26日 下午3:01:56
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/wx/vist")
public class WxVistController extends WeixinAction{
	@Autowired
	ClientService clientService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/vist/list");
		//解决苹果手机有个返回。有时候返回最起始的链接。
		try{
			checkSession(request);
		}catch(Exception e){
			init(request,response);
			if(getWeixinLoginInfo()!=null && getWeixinLoginInfo().length()>0){
				return error("",getWeixinLoginInfo());
			}
		}		
		return mv;
	}
	
	//查询
	@RequestMapping("queryList")
	public ModelAndView queryList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/vist/list");
		try{
			checkSession(request);
			//查询 返回数据

			ClientVO clientVO=(ClientVO) getBean(ClientVO.class,request);
			int currenPage=1;

			AuthorUser user=getUser(request);
			clientVO.setSid(user.getUserId().intValue());
			clientVO.setSysOrganCode(user.getOrganCode());
			mv.addObject("page", clientService.getVistList(currenPage,clientVO));
			
			mv.addObject("clientVO", clientVO);			
		}catch(Exception e){
			return error("", e.getMessage());
		}
		return mv; 
	}
	
	//打开页面
	@RequestMapping("addTrace")
	public ModelAndView pageInfo(HttpServletRequest request,String clientId,String agentType){
		ModelAndView mv=new ModelAndView("weixin/urge/info");
		mv.addObject("agentType", agentType);
		try{
			checkSession(request);
			//取值 返回页面
			
			System.out.println("get clientId>>:"+clientId);
		}catch(Exception e){
			return error("", e.getMessage());
		}
		return mv; 
	}
}
