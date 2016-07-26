package com.jiezh.content.base.modulrurl.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.group.service.BaseGroupService;
import com.jiezh.content.base.modulrurl.service.BaseModurleUrlService;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.base.modulgroup.GroupModuleVO;
import com.jiezh.dao.base.modulgroup.ModuleGroupVO;
import com.jiezh.dao.base.modulrurl.ModuleUrlVO;
/*** 
* @ClassName: ModurleurlController 
* @Description: 菜单管理的入口类，完成菜单的增 删 改 查 和菜单组的管理
* @author 陈继龙  
* @date 2016年1月18日 上午10:31:31 
 */
@Controller
@Scope("prototype")
@RequestMapping("/base/modurleurl/")
public class ModurleurlController extends WebAction{
	@Autowired
	BaseModurleUrlService baseModurleUrlService;
	@Autowired
	BaseGroupService baseGroupService;
	/**
	* @Title: index 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return
	* @param 
	* @throws Exception 参数 
	* @return ModelAndView
	* @author 陈继龙  
	* @date 2016年1月18日 上午10:31:31
	 */
	@RequestMapping("index")
	public ModelAndView index() throws Exception{
		ModelAndView mv=new ModelAndView("base/modurleurl/list");
		ModuleUrlVO recode=new ModuleUrlVO();
		recode=(ModuleUrlVO) this.getBean(recode.getClass());
		mv.addObject("page", baseModurleUrlService.search(9999999,recode));
		
		ModuleGroupVO record=new ModuleGroupVO();
		List<ModuleGroupVO> list=baseGroupService.getSelectGroupAll(record);
		mv.addObject("groupPage",list ); //  
		return mv;
	}
	/**
	* @Title: queryList 
	* @Description: 菜单查询功能，返回到查询界面
	* @return ModelAndView
	* @throws Exception 
	 */
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception{
		ModelAndView mv=new ModelAndView("base/modurleurl/list");
		int currenPage=1;
		if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage")==null?"1":request.getParameter("currenPage"));
		}
		ModuleUrlVO recode=new ModuleUrlVO();
		recode=(ModuleUrlVO) this.getBean(recode.getClass());
		mv.addObject("page", baseModurleUrlService.search(currenPage,recode));
		return mv;
	}
	/**
	* @Title: save 
	* @Description:保持提交信息，根据菜单ID区分是新增或者更新操作
	* @return ModelAndView
	* @throws Exception 
	 */
	@RequestMapping("save")
	public ModelAndView save() throws Exception{
		ModuleUrlVO moduleUrlform=new ModuleUrlVO();
		moduleUrlform=(ModuleUrlVO) this.getBean(moduleUrlform.getClass());
		moduleUrlform.setCreateDate(new Date());
		if(moduleUrlform.getModuleId()==0){
		baseModurleUrlService.save(moduleUrlform);
		}else{
		baseModurleUrlService.updateModurle(moduleUrlform);
		}
		return index();
	}
     /**
      * 
     * @Title: editormodurle 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param id
     * @throws  Exception    
     * @return  ModuleUrlVO    
      */
	@RequestMapping("editormodurle")
	@ResponseBody
	public ModuleUrlVO editormodurle(@RequestParam(value="id",required=false, defaultValue="0") long id) throws Exception{
       ModuleUrlVO  vo=baseModurleUrlService.findOneModuleUr(id); 
	   return vo;
		
	}
	
	@RequestMapping("editorgroup")
	@ResponseBody
	public ModuleGroupVO editorgroup(@RequestParam(value="id",required=false, defaultValue="0") long id) throws Exception{
		ModuleGroupVO  vo=baseGroupService.findOneGroup(id);
		return vo;
		
	}
	@RequestMapping("savegroup")
	public ModelAndView savegroup() throws Exception{
		ModuleGroupVO moduleGroupform=new ModuleGroupVO();
		moduleGroupform=(ModuleGroupVO) this.getBean(moduleGroupform.getClass());
		moduleGroupform.setCreateDate(new Date());
		if(moduleGroupform.getGroupId()==0){ 
		baseGroupService.save(moduleGroupform);
		}else{
	    baseGroupService.updateGroup(moduleGroupform);
		}
		
		return index();
	}
	@RequestMapping("groupPeizhi")
	@ResponseBody
	public List<GroupModuleVO> groupPeizhi(@RequestParam(value="id",required=false, defaultValue="0") long id) throws Exception{  //返回当前组下的所有 菜单
		GroupModuleVO vo =new GroupModuleVO();
		vo.setGroupId(id); // 组ID 
	    List<GroupModuleVO> list=baseGroupService.getModuleUrByGroupId(vo);
		return list;
	}
	
	@RequestMapping("modulePeizhi")
	@ResponseBody
	public List<GroupModuleVO> modulePeizhi(@RequestParam(value="id",required=false, defaultValue="0") long id) throws Exception{  //返回当前组下的所有 菜单
		GroupModuleVO vo =new GroupModuleVO();
		vo.setModuleId(id); // moduleID  菜单ID 
	    List<GroupModuleVO> list=baseGroupService.getModuleUrByGroupId(vo);
		return list;
	}
	
	@RequestMapping("savemodurlegroup")
	public ModelAndView savemodurlegroup() throws Exception{
		String group=  request.getParameter("group_module_id")==null?"0":request.getParameter("group_module_id").trim();
		String module= request.getParameter("module_group_id")==null?"0":request.getParameter("module_group_id").trim();
		if("0".equals(group)&&module.length()>0){
			GroupModuleVO modurlegroupform =new GroupModuleVO();
			List<?> newBeans =this.getBeanList(modurlegroupform.getClass());
			baseGroupService.saveModurleGroup(newBeans,false,Long.valueOf(module));
		}else if("0".equals(module)&&group.length()>0){
			GroupModuleVO modurlegroupform =new GroupModuleVO();
			List<?> newBeans =this.getBeanList(modurlegroupform.getClass());
			baseGroupService.saveModurleGroup(newBeans,true,Long.valueOf(group));
			}	
		
		return index();
	}
	
	
	
	
	
}
