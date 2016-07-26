/**
 * 
 */
package com.jiezh.content.weixin.jieyi.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.weixin.base.WeixinAction;
import com.jiezh.content.base.weixin.tools.WeixinUtil;
import com.jiezh.content.base.weixin.tools.bean.News;
import com.jiezh.content.base.weixin.tools.bean.Text;
import com.jiezh.content.leads.product.service.ProductService;
import com.jiezh.dao.leads.client.ProductVO;

/**
 * 产品内容
 * @author cj
 * @since  2016年3月4日 下午1:56:32
 */
@Controller
@Scope("prototype")
@RequestMapping("/exchange/wx/item")
public class WxItemsController extends WeixinAction{

	@Autowired
	ProductService productService;
	
	//从菜单进来
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv=new ModelAndView("weixin/item/info");
		//解决苹果手机有个返回。有时候返回最起始的链接。
		try{
			checkSession(request);
		}catch(Exception e){
			init(request,response);
			if(getWeixinLoginInfo()!=null && getWeixinLoginInfo().length()>0){
				return error("",getWeixinLoginInfo());
			}
		}
		try {		
			List<Node> list=productService.queryProductList();
			mv.addObject("data",list);
		} catch (Exception e) {
			e.printStackTrace();
			return error("", e.getMessage());
		}
		return mv;
	}
		
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,String userCode){
		ModelAndView mv=new ModelAndView("weixin/item/list");	
		try {
			checkSession(request);
			long id = Long.parseLong(request.getParameter("id"));
			//获取当前产品信息
			ProductVO pvo=productService.queryRecord(id);
			mv.addObject("pvo",pvo);	
			List<ProductVO> list=productService.queryChildProductsByPid(id);
			mv.addObject("data",list);
			mv.addObject("isnull",list.size());
		} catch (Exception e) {
			e.printStackTrace();
			return error("", e.getMessage());
		}
		return mv; 
	}
}
