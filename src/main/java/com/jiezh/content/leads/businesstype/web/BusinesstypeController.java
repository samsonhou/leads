package com.jiezh.content.leads.businesstype.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.businesstype.service.BusinesstypeService;
import com.jiezh.dao.leads.client.LmcategoryVO;

/**
 * @author Administrator
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/businesstype")
public class BusinesstypeController extends WebAction {
	
	@Autowired
	BusinesstypeService businesstypeService;
	
	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv=new ModelAndView("leads/businesstype/list");
		Page<LmcategoryVO> page=new Page<LmcategoryVO>();
		mv.addObject("page", new PageInfo<LmcategoryVO>(page));
		mv.addObject("clientVO", new LmcategoryVO());
		return mv ;
	}
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception{
		ModelAndView mv=new ModelAndView("leads/businesstype/list");
		int currenPage=1;
		if(request.getParameter("currenPage")!=null&&!"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		LmcategoryVO lmcategoryVO =new LmcategoryVO();
		lmcategoryVO=(LmcategoryVO) this.getBean(lmcategoryVO.getClass());
		mv.addObject("findObj", lmcategoryVO);
		mv.addObject("page", businesstypeService.getSearchList(currenPage,lmcategoryVO));
		return mv ;
	}
	
	@RequestMapping("save")
	public ModelAndView save() throws Exception{
		LmcategoryVO lmcategoryVO =new LmcategoryVO();
		lmcategoryVO=(LmcategoryVO) this.getBean(lmcategoryVO.getClass());
		if(0==lmcategoryVO.getId()){
			businesstypeService.addLmcategory(lmcategoryVO);
		}else {
			businesstypeService.updateLmcategory(lmcategoryVO);
		}
		return queryList();
	}
	@RequestMapping("editor")
	@ResponseBody
	public LmcategoryVO editor(@RequestParam(value="id",required=true, defaultValue="0") long id)  throws Exception {
		LmcategoryVO lmcategoryVO  = businesstypeService.findOneLmcategory(id);
		return lmcategoryVO;
	}

}
