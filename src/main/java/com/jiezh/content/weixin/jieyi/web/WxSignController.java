
package com.jiezh.content.weixin.jieyi.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.weixin.base.WeixinAction;
import com.jiezh.content.leads.service.ClientService;


/**
 * 微信重要提醒
 * @author wp
 * @since  2016年3月4日 
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/wx/sign")
public class WxSignController extends WeixinAction{
	@Autowired
	ClientService clientService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/sign/list");	
		//解决苹果手机有个返回。有时候返回最起始的链接。
		try{
			checkSession(request);
		}catch(Exception e){
			init(request,response);
			if(getWeixinLoginInfo()!=null && getWeixinLoginInfo().length()>0){
				return error("",getWeixinLoginInfo());
			}
		}
		try{			
			AuthorUser user = getUser(request);
			String date = new SimpleDateFormat("yyyy-MM").format(new Date());
			Map<String,Object> paras = new HashMap<String,Object>();
			paras.put("user_id", user.getUserId());
			paras.put("month", date);	
			
			//不是销售不显示
//			if("0".endsWith(checkRole(Env.ROLE_SALE,request))){
//				return null;
//			}
			//重要客户查询
			List<Map<String, Object>> impClient = clientService.getTimeoutList(paras);
			mv.addObject("impClient",impClient);

		}catch(Exception e){
			e.printStackTrace();
			return error("", e.getMessage());
		}
		return mv;
	}
}
