
package com.jiezh.content.weixin.jieyi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.weixin.base.WeixinAction;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.content.leads.urge.service.LmurgeService;
import com.jiezh.content.leads.vist.web.VistBean;
import com.jiezh.dao.leads.client.ClientVO;
import com.jiezh.dao.leads.client.urge.LmurgeVO;

/**
 * 微信 崔促
 * @author liangds
 * @since  2016年2月26日 下午3:00:22
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/wx/urge")
public class WxLmurgeController extends WeixinAction{
	@Autowired
	ClientService clientService;
	@Autowired
	LmurgeService lmurgeService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/urge/list");
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
		ModelAndView mv=new ModelAndView("weixin/urge/list");
		try{
			checkSession(request);
			//查询 返回数据
			AuthorUser user=getUser(request);
			
			LmurgeVO lmurgeVO=new LmurgeVO();
			lmurgeVO=(LmurgeVO) this.getBean(lmurgeVO.getClass(),request);
			int currenPage=1;
			
			lmurgeVO.setUrgeToPersonId(user.getUserId());
			lmurgeVO.setUrgeStatus("0");
			mv.addObject("page", lmurgeService.getClientLmurge(currenPage, lmurgeVO));
			mv.addObject("findObj", lmurgeVO);			
		}catch(Exception e){
			e.printStackTrace();
			return error("", e.getMessage());
		}
		return mv; 
	}
	
	//打开页面
	@RequestMapping("pageInfo")
	public ModelAndView pageInfo(HttpServletRequest request,String clientId,String urgeid,String agentType){
		ModelAndView mv=new ModelAndView("weixin/urge/info");
		mv.addObject("agentType", agentType);
		try{
			checkSession(request);
			//取值 返回页面
			ClientVO clientVO=new ClientVO();
			clientVO.setId(Long.parseLong(clientId));
			mv.addObject("cMap", clientService.searchClient(clientVO));
			mv.addObject("trace", clientService.searchTrace(clientVO));
			
			System.out.println("get clientId>>:"+clientId);
			mv.addObject("urgeid",urgeid); // 传递催促ID
			mv.addObject("comform", "0");
		}catch(Exception e){
			return error("", e.getMessage());
		}
		return mv; 
	}
	
	//提交
	@RequestMapping("submitInfo")
	public ModelAndView submitInfo(HttpServletRequest request,String agentType
			,@RequestParam(value="urgeid",required=true, defaultValue="0") long urgeid){
		try{
			checkSession(request);
			//保存数据
			AuthorUser user=getUser(request);
			
			VistBean vistBean=new VistBean();
			vistBean.getClientVO(request);
			vistBean.getClientTraceVO(user, request);
			vistBean.setUrgeid(urgeid);
			
			String message=clientService.processClient(user, vistBean);
			

			if("1".equals(agentType)){
				return successful(Env.getWebRoot()+"/exchange/wx/urge/queryList.do",message);
			}else if("2".equals(agentType)){
				return successful(Env.getWebRoot()+"/exchange/wx/vist/queryList.do",message);
			}else if("4".equals(agentType)){
				return successful(Env.getWebRoot()+"/exchange/wx/sign/index.do",message);
			}
			return successful("","数据保存成功");
		}catch(Exception e){
			return error("", e.getMessage());
		}
	}
	
	@RequestMapping("viewInfo")
	public ModelAndView viewInfo(HttpServletRequest request,String clientId,String urgeid,String agentType){
		try{
			ModelAndView mv=new ModelAndView("weixin/urge/info");
			mv.addObject("agentType", agentType);
			System.out.println(clientId);
			//返回数据
			
			//取值 返回页面
			ClientVO clientVO=new ClientVO();
			clientVO.setId(Long.parseLong(clientId));
			mv.addObject("cMap", clientService.searchClient(clientVO));
			mv.addObject("trace", clientService.searchTrace(clientVO));
			
			System.out.println("get clientId>>:"+clientId);
			//来源标识 
			String comform=request.getParameter("comform");
			if("1".equals(comform)){
				comform="1";
			}else{
				comform="0";
			}
			mv.addObject("urgeid",urgeid); // 传递催促ID
			mv.addObject("comform", comform);
			
			return mv;
		}catch(Exception e){
			return error("", e.getMessage());
		}
	
	}
	
	@RequestMapping(value = "getExists")
	@ResponseBody
	public String getExists(HttpServletResponse response,String connum) throws Exception {		
		String result = clientService.getIsExistsOfClient(connum);
		response.getWriter().print(result);
		return null;
	}
}
