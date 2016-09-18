/**
 * 
 */
package com.jiezh.content.leads.track.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.track.service.AgingTrackService;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 时效追踪controller类
 * 
 * @author houjiaqiang
 * @since 2016年3月14日 上午10:29:35
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/agingtrack")
public class AgingTrackController extends WebAction {
	
	@Resource
	AgingTrackService agingTrackService;

	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv = new ModelAndView("leads/track/list");
		return mv;
	}

	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception {
		ModelAndView mv = new ModelAndView("leads/track/list");
		String agingTrackType = request.getParameter("agingTrackType");
		mv.addObject("agingTrackType", agingTrackType);
		AuthorUser user = this.getUser();
		ClientVO clientVo = new ClientVO();
		clientVo.setSid(user.getUserId().intValue());
		clientVo.setSysOrganCode(user.getOrganCode());
		clientVo.setAgingTrackType(agingTrackType);
		int currenPage=1;
		if(request.getParameter("currenPage")!=null && !"".equals(request.getParameter("currenPage"))){
			currenPage=Integer.parseInt(request.getParameter("currenPage"));
		}
		mv.addObject("page", agingTrackService.queryClientByType(currenPage, clientVo));
		return mv;
	}
}
