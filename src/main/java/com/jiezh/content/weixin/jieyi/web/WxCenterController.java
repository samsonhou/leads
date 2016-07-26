
package com.jiezh.content.weixin.jieyi.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


/**
 * 微信 用户中心
 * @author wp
 * @since  2016年3月4日 
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/wx/center")
public class WxCenterController extends WeixinAction{
	@Autowired
	ClientService clientService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/center/list");
		init(request,response);
		if(getWeixinLoginInfo()!=null && getWeixinLoginInfo().length()>0){
			return error("",getWeixinLoginInfo());
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
			
			Map<String,Object> plan = clientService.getPlan(paras);
			String totalScore = clientService.getTotalScore(paras);
			Object[] score = clientService.getScore(paras);
			//本月减分
			mv.addObject("subtract",score[0]==null?"0":score[0]);
			//本月加分
			mv.addObject("add",score[1]==null?"0":score[1]);		
			//任务量
			mv.addObject("plan_num",plan==null?"未设置":plan.get("PLAN_NUM"));
			//完成数量
			if(plan!=null){
				mv.addObject("perform",plan.get("PERFORM"));
				//未完成数量
				int num = (Integer.parseInt(plan.get("PLAN_NUM")+"")==0?0:Integer.parseInt(plan.get("PLAN_NUM")+"")-Integer.parseInt(plan.get("PERFORM")+""));
				mv.addObject("num",num<0?0:num);
			}
			//当月积分
			mv.addObject("sum",totalScore);
		}catch(Exception e){
			e.printStackTrace();
			return error("", e.getMessage());
		}
		return mv;
	}
}
