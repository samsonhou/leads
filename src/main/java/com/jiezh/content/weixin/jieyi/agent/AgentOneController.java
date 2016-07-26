/**
 * 
 */
package com.jiezh.content.weixin.jieyi.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jiezh.content.base.weixin.base.AgentAction;

/**
 * 企业号应用回调验证
 * 跟踪回复-int
 * @author liangds
 * @since  2016年2月24日 下午3:47:29
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/agent/1")
public class AgentOneController extends AgentAction{

	public AgentOneController(){
		agentId="5";
	}
	
	@RequestMapping("sh")
	public void sh(HttpServletRequest request,HttpServletResponse response){
		execute(request,response);
	}
}
