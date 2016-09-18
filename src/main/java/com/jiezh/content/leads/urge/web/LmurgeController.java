package com.jiezh.content.leads.urge.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.urge.service.LmurgeService;
import com.jiezh.dao.leads.client.urge.LmurgeVO;

/** 
* @ClassName: LmurgeController 
* @Description: 线索催促管理控制类
* @author 陈继龙  
* @date 2016年1月25日 下午5:48:29 
*  
*/
@Controller
@Scope("prototype")
@RequestMapping("/leads/urge")
public class LmurgeController extends WebAction {
    @Autowired
    LmurgeService lmurgeService;
    
    @RequestMapping("index") // /leads/urge/index.do
    public ModelAndView index() throws Exception {
	ModelAndView mv=new ModelAndView("leads/urge/lmurgelist");
	AuthorUser user=getUser();
	LmurgeVO lmurgevo=new LmurgeVO();
	lmurgevo.setUrgeToPersonId(user.getUserId());
	Page<LmurgeVO> page=new Page<LmurgeVO>();
	mv.addObject("page", new PageInfo<LmurgeVO>(page));
	mv.addObject("findObj",lmurgevo);
	return mv ;
    }
    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception{
	ModelAndView mv=new ModelAndView("leads/urge/lmurgelist");
	LmurgeVO lmurgeVO=new LmurgeVO();
	lmurgeVO=(LmurgeVO) this.getBean(lmurgeVO.getClass());
	int currenPage=1;
	if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
		currenPage=Integer.parseInt(request.getParameter("currenPage"));
	}
	
	mv.addObject("page", lmurgeService.getClientLmurge(currenPage, lmurgeVO));
	mv.addObject("findObj", lmurgeVO);
	return mv ;
    }
    
    
}
