package com.jiezh.content.base.user.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.user.service.LockUserService;
import com.jiezh.dao.base.user.UserVO;

/**
 * 锁定用户
 * 
 * @author houjiaqiang
 * @since 2016-02-22
 */
@Controller
@Scope("prototype")
@RequestMapping("/base/user/lock/")
public class LockUserController extends WebAction {
	@Autowired
	LockUserService lockUserService;

	/**
	 * 锁定用户查询页面
	 * 
	 * @return Exception
	 */
	@RequestMapping("index")
	public ModelAndView index() throws Exception {
		ModelAndView mv = new ModelAndView("base/user/lockUser");
		UserVO userVo = getUser();
		mv.addObject("orgId", userVo.getOrganId());
		return mv;
	}

	/**
	 * 
	 * 锁定用户查询
	 * 
	 * @param
	 * @return Exception
	 */
	@RequestMapping("queryList")
	public ModelAndView queryList() throws Exception {
		ModelAndView mv = new ModelAndView("base/user/lockUser");
		UserVO userVo = (UserVO) this.getBean(UserVO.class);
		if ("00".equals(userVo.getOrganId()))
			userVo.setOrganId("");
		int currenPage = 1;
		if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
			currenPage = Integer.parseInt(request.getParameter("currenPage"));
		}
		mv.addObject("page", lockUserService.queryLockUser(currenPage, userVo));
		mv.addObject("userVo", userVo);
		mv.addObject("orgId", getUser().getOrganId());
		return mv;
	}

	/**
	 * 用户解锁
	 * 
	 * @param id
	 *            锁定用户数据Id
	 * @return Exception
	 */
	@RequestMapping("unlock")
	public ModelAndView unlockUser(@RequestParam(value = "id") long id) throws Exception {
		ModelAndView mv = new ModelAndView("base/user/lockUser");
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("oprUserCode", this.getUser().getUserCode());
		UserVO userVo = (UserVO) this.getBean(UserVO.class);
		if ("00".equals(userVo.getOrganId()))
			userVo.setOrganId("");
		lockUserService.modifyUserLock(params);
		int currentPage = 1;
		if (request.getParameter("currentPage") != null && !"".equals(request.getParameter("currentPage"))) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		mv.addObject("page", lockUserService.queryLockUser(currentPage, userVo));
		mv.addObject("userVo", userVo);
		mv.addObject("orgId", getUser().getOrganId());
		return mv;
	}

	/**
	 * 用户批量解锁
	 * 
	 * @param ids
	 *            锁定用户数据Id
	 * @return Exception
	 */
	@RequestMapping("unlockBatch")
	public ModelAndView unlockBatch(@RequestParam(value = "ids") String ids) throws Exception {
		ModelAndView mv = new ModelAndView("base/user/lockUser");
		Map<String, Object> params = new HashMap<>();
		params.put("ids", Arrays.asList(ids.split(",")));
		params.put("oprUserCode", this.getUser().getUserCode());
		UserVO userVo = (UserVO) this.getBean(UserVO.class);
		if ("00".equals(userVo.getOrganId()))
			userVo.setOrganId("");
		lockUserService.ModifyBatchUserLock(params);
		int currentPage = 1;
		if (request.getParameter("currentPage") != null && !"".equals(request.getParameter("currentPage"))) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		mv.addObject("page", lockUserService.queryLockUser(currentPage, userVo));
		mv.addObject("userVo", userVo);
		mv.addObject("orgId", getUser().getOrganId());
		return mv;
	}

}
