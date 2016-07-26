/**
 * 
 */
package com.jiezh.content.weixin.jieyi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.weixin.base.WeixinAction;
import com.jiezh.content.base.weixin.tools.WeixinUtil;
import com.jiezh.content.base.weixin.tools.bean.News;
import com.jiezh.content.base.weixin.tools.bean.Text;

/**
 * demo test
 * @author liangds
 * @since  2016年2月25日 下午1:56:32
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/demo")
public class DemoController extends WeixinAction{

	//从菜单进来
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/demo/list");
		init(request,response);
		if(getWeixinLoginInfo()!=null && getWeixinLoginInfo().length()>0){
			return error("",getWeixinLoginInfo());
		}
		return mv;
	}
	
	//内部跳转 用户ID需传递
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,String userCode){
		ModelAndView mv=new ModelAndView("weixin/demo/list");
		mv.addObject("userCode", getUserId(request));
		if("1".equals(userCode)){
			return successful(Env.getWebRoot()+"/exchange/demo/list.do","操作成功了");
		}else if("2".equals(userCode)){
			return error("", "保存失败了");
		}
		return mv; 
	}
	
	@RequestMapping("info")
	public ModelAndView info(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("weixin/demo/info");
		mv.addObject("userCode",getUserId(request));
		
		//1.发送text
		Text txt=new Text();
		txt.setTouser("JY888");
		txt.setAgentid("3");
		txt.setContent("测试发送消息");
		WeixinUtil.postTxt("3", txt);
		
		//2.发送news
		News news=new News();
		news.setTouser("JY888");
		news.setAgentid("3");
		news.setTitle("客服催促-张英");
		news.setDescription("客户名称:李先生\r\n客户电话：15810511234\r\n催促原因:客户着急,尽快与客户联系");
		news.setUrl("http://wpp.tunnel.phpor.me/leads/exchange/wx/urge/viewInfo.do");
		WeixinUtil.postNews("3", news);
		
		return mv;
	}
	
	//queryList.do
	//info.do
	//processs.do
	
	
	
}
