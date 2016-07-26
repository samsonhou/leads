/**  

* @Title: OrganCompany.java 

* @Package com.jiezh.content.base.organcompany.web 

* @Description: TODO(用一句话描述该文件做什么) 

* @author A18ccms A18ccms_gmail_com  

* @date 2015年12月10日 上午9:16:52 

* @version V1.0  

*/ 
package com.jiezh.content.base.organcompany.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.group.service.BaseGroupService;
import com.jiezh.dao.base.codetype.CodeTypeVO;
import com.jiezh.dao.base.organcompany.OrganCompanyVO;
import com.jiezh.content.base.organcompany.service.BaseOrganCompanyService;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.pub.web.WebTools;

/** 

* @ClassName: OrganCompany 

* @Description: 机构管理信息入口 

* @author 陈继龙  E-mail:  cqcnihao@139.com *

* @date 2015年12月10日 上午9:16:52 
 

*/
@Controller
@Scope("prototype")
@RequestMapping("/base/organcompany/")
public class OrganCompanyController extends WebAction{
	
	@Autowired
	BaseGroupService baseGroupService;

	@Autowired
	BaseOrganCompanyService baseOrganCompanyService ;
	
	@RequestMapping("index")
	public  ModelAndView index() throws Exception{
		ModelAndView mv=new ModelAndView("base/organcompany/list");
		Page<CodeTypeVO> page=new Page<CodeTypeVO>();
		AuthorUser voUser=getUser();
		OrganCompanyVO vo=new OrganCompanyVO();
		vo.setOrganId(voUser.getOrganId());
		vo.setOrganCode(voUser.getOrganCode());
		mv.addObject("findObj", vo);
		mv.addObject("page", new PageInfo<CodeTypeVO>(page));
		return mv;
		
	}
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception{
		ModelAndView mv=new ModelAndView("base/organcompany/list");
		OrganCompanyVO vo=new OrganCompanyVO();
		vo=(OrganCompanyVO) this.getBean(vo.getClass());
		int currenPage=1;
		if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		mv.addObject("page", baseOrganCompanyService.search(currenPage,vo));
		mv.addObject("findObj", vo);
		
		return mv ;
	}
	@RequestMapping("save")
	public ModelAndView save() throws Exception {
		AuthorUser voUser=getUser();
		OrganCompanyVO organCompanyForm=new OrganCompanyVO(); 
		organCompanyForm=(OrganCompanyVO) this.getBean(organCompanyForm.getClass());
		organCompanyForm.setOrganCode(voUser.getOrganCode()); //当前用户只能保存自己公司的组织 
		if("1".equals(organCompanyForm.getIsnew())){
			baseOrganCompanyService.addOrganCompany(organCompanyForm);
		}else
			baseOrganCompanyService.updateOrganCompany(organCompanyForm);
		return queryList();
	}
	@RequestMapping("editor")
	@ResponseBody
	public OrganCompanyVO editor() throws Exception {
		//ModelAndView mv=new ModelAndView("base/codetype/list");
		AuthorUser voUser=getUser();
		OrganCompanyVO organCompanyEditor=new OrganCompanyVO();
		organCompanyEditor=(OrganCompanyVO) this.getBean(organCompanyEditor.getClass());
		organCompanyEditor.setSysOrganCode(voUser.getOrganCode());
		if("ALL".equals(voUser.getOrganCode())){
			   
		}else{
		 organCompanyEditor.setOrganCode(voUser.getOrganCode()); //当前用户只能编辑自己公司的组织 
		}
		organCompanyEditor=baseOrganCompanyService.findOne(organCompanyEditor.getOrganId(),organCompanyEditor.getOrganCode());
		//mv.addObject("codeTypeEditor", codeTypeEditor);
		//设成当前机构
		organCompanyEditor.setSysOrganCode(WebTools.getOrganLevel(organCompanyEditor.getOrganCode(), organCompanyEditor.getOrganId(),"00"));
		return organCompanyEditor;
	}
	@RequestMapping("editorstatus")
	@ResponseBody
	public OrganCompanyVO editorstatus(@RequestParam(value="status",required=true, defaultValue="0") String status,
			@RequestParam(value="organId",required=true, defaultValue="0") String organId) throws Exception {
		OrganCompanyVO organCompanyEditorStatus=new OrganCompanyVO();
		AuthorUser voUser=getUser();
		organCompanyEditorStatus=baseOrganCompanyService.findOne(organId,voUser.getOrganCode());
		organCompanyEditorStatus.setStatus(status);
		
		baseOrganCompanyService.updateOrganCompany(organCompanyEditorStatus);
		return organCompanyEditorStatus;
	}
	
	@RequestMapping("queryOrgans")
	@ResponseBody
	public String queryOrgans() throws IOException{
	    Map<String, Object> params = new HashMap<>();
	    String str =baseOrganCompanyService.findOrgans(params);
	    response.getWriter().print(str) ;
	    return null;
	}
	

}
