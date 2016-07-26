/**
 * 
 */
package com.jiezh.content.leads.exchange;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 推送
 * @author liangds
 * @since  2016年1月20日 上午9:10:47
 */
@Controller
@RequestMapping("/exchange/")
public class ExchangeController {

	@RequestMapping("pushMsg")
	public void pushMsg(HttpServletRequest request,HttpServletResponse response){
		try{
			response.setHeader("pragma", "no-cache,no-store");
		    response.setHeader("cache-control", "no-cache,no-store,max-age=0,max-stale=0");
		    response.setContentType("text/event-stream");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			//writer.write("retry: 15000\n");
			writer.write("data: "+ QueueTools.getTaskId()+"\n\n");
			writer.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
