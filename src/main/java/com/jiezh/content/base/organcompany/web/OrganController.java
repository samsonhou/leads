/**  

* @Package com.jiezh.content.base.organcompany.web 

* @Description: TODO(用一句话描述该文件做什么) 

* @author A18ccms A18ccms_gmail_com  

* @date 2015年12月17日 下午6:26:30 

* @version V1.0  

*/ 
package com.jiezh.content.base.organcompany.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.organ.service.BaseOrganService;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.base.codetype.CodeTypeVO;
import com.jiezh.dao.base.organ.OrganVO;

/** 

* @ClassName: OrganController 

* @Description: TODO(这里用一句话描述这个类的作用) 

* @author 陈继龙  E-mail:  cqcnihao@139.com *

* @date 2015年12月17日 下午6:26:30 
 

*/
@Controller
@Scope("prototype")
@RequestMapping("/base/organ/")
public class OrganController extends WebAction{
	@Autowired
	BaseOrganService baseOrganService;
	
	@RequestMapping("index")
	public  ModelAndView index() throws Exception{
		ModelAndView mv=new ModelAndView("base/organ/list");
		Page<CodeTypeVO> page=new Page<CodeTypeVO>();
		OrganVO vo=new OrganVO();
		mv.addObject("findObj", vo);
		mv.addObject("page", new PageInfo<CodeTypeVO>(page));
		return mv;
	}
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception{
		ModelAndView mv=new ModelAndView("base/organ/list");
		OrganVO vo=new OrganVO();
		vo=(OrganVO) this.getBean(vo.getClass());
		int currenPage=1;
		if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		mv.addObject("page", baseOrganService.search(currenPage,vo));
		mv.addObject("findObj", vo);
		
		return mv ;
	}
	
	@RequestMapping("save")
	public ModelAndView save() throws Exception {
		OrganVO organForm=new OrganVO(); 
		organForm=(OrganVO) this.getBean(organForm.getClass());
		if("1".equals(organForm.getIsnew())){
			baseOrganService.addOrgan(organForm);
		}else
			baseOrganService.updateOrgan(organForm);
		return queryList();
	}
	
	@RequestMapping("editor")
	@ResponseBody
	public OrganVO editor() throws Exception {
		OrganVO organEditor=new OrganVO();
		organEditor=(OrganVO) this.getBean(organEditor.getClass());
		organEditor=baseOrganService.findOne(organEditor.getId());
		return organEditor;
	}
}
