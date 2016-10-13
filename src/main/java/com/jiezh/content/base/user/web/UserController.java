package com.jiezh.content.base.user.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.group.service.BaseGroupService;
import com.jiezh.content.base.login.model.GroupBean;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.pub.web.WebTools;
import com.jiezh.content.base.user.service.BaseUserService;
import com.jiezh.dao.base.modulgroup.ModuleGroupVO;
import com.jiezh.dao.base.user.RoleVO;
import com.jiezh.dao.base.user.UserVO;

/**
 * 用户管理
 * 
 * @author liangds
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/base/user/")
public class UserController extends WebAction {

    @Autowired
    BaseUserService baseUserService;

    @Autowired
    BaseGroupService baseGroupService;

    /***
     * 
     * @Title: index
     * @Description: 用户管理的入口方法，负责跳转到用户管理页面。
     * @throws Exception
     * @return ModelAndView
     * @author 陈继龙
     * @date 2016年1月18日
     */
    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("base/user/list");
        Page<UserVO> page = new Page<UserVO>();
        mv.addObject("page", new PageInfo<UserVO>(page));
        AuthorUser voUser = getUser();
        mv.addObject("organId1", voUser.getOrganId());
        return mv;
    }

    /***
     * 
     * @Title: queryList
     * @throws Exception
     * @return ModelAndView
     * @author 陈继龙
     * @date 2016年1月18日
     */
    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("base/user/list");
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        UserVO findVo = new UserVO();
        String organId1 = request.getParameter("organId1");
        findVo = (UserVO) this.getBean(findVo.getClass());
        AuthorUser voUser = getUser();
        if ("ALL".equalsIgnoreCase(voUser.getOrganCode())) {

        } else {
            findVo.setOrganCode(voUser.getOrganCode()); // 当前用户的组织

            // findVo.setOrganId(voUser.getOrganId());
            findVo.setOrganId(StringUtils.isEmpty(organId1) ? voUser.getOrganId() : organId1); // 当前用户的机构 modify by cj
        }
        // 角色查询
        String roleId = request.getParameter("roleId");
        findVo.setRoleId(roleId);

        mv.addObject("page", baseUserService.search(currenPage, findVo));
        mv.addObject("findObj", findVo);
        mv.addObject("organId1", voUser.getOrganId());
        mv.addObject("roleId", roleId);
        return mv;
    }

    @RequestMapping("editor")
    @ResponseBody
    public UserVO editor(@RequestParam(value = "userId", required = false, defaultValue = "0") long userId) throws Exception {
        UserVO vo = new UserVO();
        vo = baseUserService.findOne(userId);
        AuthorUser voUser = getUser();
        RoleVO roleVO = new RoleVO();
        // modify by hjq for JZLM-89
        if ("3".equals(voUser.getOrganLevel())) {
            vo.setOrganId(voUser.getOrganId());
            vo.setOrganCode(voUser.getOrganCode());
            vo.setOrganName(voUser.getOrganName());
            vo.setOrganCompanyName(voUser.getOrganCompanyName());
            roleVO.setId(2); // 2 为销售 角色
        }
        // if (!"00".equals(voUser.getOrganId())) { // 分公司人 员
        // vo.setOrganId(voUser.getOrganId());
        // vo.setOrganCode(voUser.getOrganCode());
        // vo.setOrganName(voUser.getOrganName());
        // vo.setOrganCompanyName(voUser.getOrganCompanyName());
        // roleVO.setId(2); // 2 为销售 角色
        // }
        List<RoleVO> usreRoles = baseUserService.selectUsreRoles(roleVO); // 全部角色
        List<RoleVO> selectrole = baseUserService.selectUsreRolesByUserId(userId); // 当前用户选择过的角色
        for (int i = 0; i < usreRoles.size(); i++) {
            RoleVO roleObj = (RoleVO) usreRoles.get(i);
            for (int j = 0; j < selectrole.size(); j++) {
                RoleVO selectroleObj = (RoleVO) selectrole.get(j);
                if (selectroleObj.getId() == roleObj.getId()) { // 当前用户的角色中已经有该角色的值
                    roleObj.setStatus("99"); // 标志位选中状态，该用户已经选中了该角色
                }
            }
        }
        // 设成当前机构
        vo.setSysOrganCode(WebTools.getOrganLevel(vo.getOrganCode(), vo.getOrganId(), "00"));
        vo.setTemproles(usreRoles);
        return vo;
    }

    @RequestMapping("editorPassword")
    @ResponseBody
    public void editorPassword(@RequestParam(value = "userId", required = false, defaultValue = "0") long userId,
        @RequestParam(value = "password", required = true, defaultValue = "123456") String password,
        @RequestParam(value = "ispass", required = true, defaultValue = "0") String ispass) throws Exception {
        UserVO vo = new UserVO();
        vo.setUserId(userId);
        vo.setIsPass(ispass);
        vo.setPassword(new Md5PasswordEncoder().encodePassword(password, ""));
        baseUserService.updateModurle(null, vo);
    }

    @RequestMapping("increased")
    @ResponseBody
    public UserVO increased() throws Exception {
        UserVO vo = new UserVO();
        AuthorUser voUser = getUser();
        RoleVO roleVO = new RoleVO();
        // modify by hjq for JZLM-89
        if ("3".equals(voUser.getOrganLevel())) {
            vo.setOrganId(voUser.getOrganId());
            vo.setOrganCode(voUser.getOrganCode());
            vo.setOrganName(voUser.getOrganName());
            vo.setOrganCompanyName(voUser.getOrganCompanyName());
            roleVO.setId(2); // 2 为销售 角色
        }
        // if (!"00".equals(voUser.getOrganId())) { // 分公司人 员
        // vo.setOrganId(voUser.getOrganId());
        // vo.setOrganCode(voUser.getOrganCode());
        // vo.setOrganName(voUser.getOrganName());
        // vo.setOrganCompanyName(voUser.getOrganCompanyName());
        // roleVO.setId(2); // 2 为销售 角色
        // }

        List<RoleVO> list = baseUserService.selectUsreRoles(roleVO);
        vo.setTemproles(list);

        return vo;
    }

    /***
     * @Title: save
     * @param @return
     * @param @throws Exception
     * @return ModelAndView
     * @author 陈继龙
     * @date 2016年1月18日
     */
    @RequestMapping("save")
    public ModelAndView save() throws Exception {
        String[] roles = request.getParameterValues("roles"); // 得到选择中的角色
        UserVO Uservoform = new UserVO();
        Uservoform = (UserVO) this.getBean(Uservoform.getClass());
        Uservoform.setUserName(Uservoform.getUserCode());
        AuthorUser voUser = getUser();
        if ("ALL".equalsIgnoreCase(voUser.getOrganCode())) {

        } else {
            Uservoform.setOrganCode(voUser.getOrganCode());
        }
        if ("".equals(Uservoform.getPassword()) || null == Uservoform.getPassword() || Uservoform.getPassword().length() == 0) {
            Uservoform.setPassword(null);
        } else {
            Uservoform.setPassword(new Md5PasswordEncoder().encodePassword(Uservoform.getPassword(), ""));
        }

        if (Uservoform.getUserId() == 0) {
            baseUserService.save(roles, Uservoform);
        } else {
            baseUserService.updateModurle(roles, Uservoform);
        }
        return queryList();
    }

    @RequestMapping("toAssgin")
    public ModelAndView toAssgin(@RequestParam(value = "userId", required = true, defaultValue = "0") long userId) throws Exception {
        ModelAndView mv = new ModelAndView("base/user/listassgin");
        UserVO uservo = baseUserService.findOne(userId);
        ModuleGroupVO moduleGroup = new ModuleGroupVO();
        AuthorUser voUser = getUser();
        if (!"00".equals(voUser.getOrganId())) { // 分公司人 员
            moduleGroup.setGroupId(301); // 分公司管理员 ，只需要分配线索 -销售权限即可，其他不需要
        }

        // 菜单组
        List<ModuleGroupVO> groupList = baseGroupService.getSelectGroupAll(moduleGroup);
        List<ModuleGroupVO> selectgrouplist = baseGroupService.getSelectGroupAllByUserId(userId);

        mv.addObject("selectgrouplist", selectgrouplist);
        mv.addObject("groupList", groupList);
        mv.addObject("uservo", uservo);
        return mv;
    }

    @RequestMapping("saveUsergroup")
    public ModelAndView saveUserGroup() throws Exception {
        String userId = request.getParameter("module_userId_id") == null ? "0" : request.getParameter("module_userId_id").trim();
        GroupBean groupBean = new GroupBean();
        List<?> newBeans = this.getBeanList(groupBean.getClass());
        baseUserService.saveUserGroup(newBeans, Long.valueOf(userId));
        return toAssgin(Long.valueOf(userId));
    }

    @RequestMapping("ajax")
    @ResponseBody
    public Map<String, String> getOrganByOrganId(String organId, String defVal) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        result.put("select", baseUserService.getOrganInfo(organId, getUser().getOrganCode(), defVal));
        return result;
    }

    @RequestMapping("queryListOfAdBook")
    public ModelAndView queryListOfAdBook() throws Exception {
        ModelAndView mv = new ModelAndView("base/user/listOfAdBook");
        String roleid = request.getParameter("roleId");
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        UserVO findVo = new UserVO();
        findVo = (UserVO) this.getBean(findVo.getClass());

        findVo.setIsPass(roleid);
        AuthorUser voUser = getUser();
        if ("ALL".equalsIgnoreCase(voUser.getOrganCode())) {

        } else {
            findVo.setOrganCode(voUser.getOrganCode()); // 当前用户的组织
            findVo.setOrganId(voUser.getOrganId()); // 当前用户的机构
        }
        mv.addObject("page", baseUserService.searchOfAdBook(currenPage, findVo));
        mv.addObject("findObj", findVo);
        mv.addObject("roleId", roleid);
        return mv;
    }

    @RequestMapping("checkuseronly")
    @ResponseBody
    public Map<String, String> checkUserOnly(@RequestParam(value = "userCode", required = false, defaultValue = "0") String userCode,
        @RequestParam(value = "userId", required = false, defaultValue = "0") long userId) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        result.put("ischeck", baseUserService.checkUserOnly(userCode, userId));
        return result;
    }

    @RequestMapping("userTree")
    @ResponseBody
    public void userTree() throws Exception {
        AuthorUser user = getUser();
        String roles = request.getParameter("roles");
        Map<String, Object> param = new HashMap<>();
        param.put("roles", Arrays.asList(roles.split(",")));
        param.put("organId", user.getOrganId());

        response.getWriter().print(baseUserService.getUserAndOrg(param));
    }
}
