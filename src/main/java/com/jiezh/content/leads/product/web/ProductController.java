package com.jiezh.content.leads.product.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.product.service.ProductService;
import com.jiezh.dao.base.modulgroup.GroupModuleVO;
import com.jiezh.dao.leads.client.ProductVO;

/** 
* @ClassName: ProductController 
* @Description: 产品内容控制类
* @author wp 
* @date 2016年2月
*  
*/
@Controller
@Scope("prototype")
@RequestMapping("/leads/product/")
public class ProductController extends WebAction {
    @Autowired
    ProductService productService;
    
    @RequestMapping("index") // 
    public ModelAndView index() throws Exception {
		ModelAndView mv=new ModelAndView("leads/product/productinfo");
		//是否总公司管理员
		boolean ifCManager = false;
		AuthorUser user=getUser();
		if(user.getOrganId().equals("00")){
			if(this.checkRole("1").equals("1"))
				ifCManager = true;
		}
		if(ifCManager)
			mv.addObject("ifCManager","true");
		else
			mv.addObject("ifCManager","false");
		return mv ;
    }
    
    @RequestMapping("modify")
    @ResponseBody
	public ModelAndView modifyProduct() throws Exception {
    	ModelAndView mv=new ModelAndView("leads/product/productinfo");
    	ProductVO productVO=(ProductVO) getBean(ProductVO.class);
    	productService.update(productVO);
    	
		//是否总公司管理员
		boolean ifCManager = false;
		AuthorUser user=getUser();
		if(user.getOrganId().equals("00")){
			if(this.checkRole("1").equals("1"))
				ifCManager = true;
		}
		if(ifCManager)
			mv.addObject("ifCManager","true");
		else
			mv.addObject("ifCManager","false");  
		mv.addObject("message", "已修改成功!");
		return mv;
	}
    
    @RequestMapping("add")
    @ResponseBody
	public ModelAndView addProduct() throws Exception {
    	ModelAndView mv=new ModelAndView("leads/product/productinfo");
    	ProductVO productVO=(ProductVO) getBean(ProductVO.class);
    	productService.insert(productVO);
    	
		//是否总公司管理员
		boolean ifCManager = false;
		AuthorUser user=getUser();
		if(user.getOrganId().equals("00")){
			if(this.checkRole("1").equals("1"))
				ifCManager = true;
		}
		if(ifCManager)
			mv.addObject("ifCManager","true");
		else
			mv.addObject("ifCManager","false");    
		mv.addObject("message", "已保存成功!");
		return mv;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ModelAndView removeProduct() throws Exception {
		ModelAndView mv=new ModelAndView("leads/product/productinfo");
		String pid = request.getParameter("id");
		productService.deleteProduect(Long.parseLong(pid));
		
		//是否总公司管理员
		boolean ifCManager = false;
		AuthorUser user=getUser();
		if(user.getOrganId().equals("00")){
			if(this.checkRole("1").equals("1"))
				ifCManager = true;
		}
		if(ifCManager)
			mv.addObject("ifCManager","true");
		else
			mv.addObject("ifCManager","false");		
		return mv;
	}   
	
	/**
	 * @throws Exception
	 * 查询内容
	 */
	@RequestMapping(value = "queryProducts")
	@ResponseBody
	public void queryProducts() throws Exception {

		String str0 = productService.queryProducts();
		
		response.getWriter().print(str0);
	}
	/**
	 * @throws Exception
	 * 查询一个具体的记录
	 */
	@RequestMapping("queryRecord")
	@ResponseBody
	public ProductVO queryRecord(long id) throws Exception{ 
	    ProductVO productVO=productService.queryRecord(id);
		return productVO;
	}
}
