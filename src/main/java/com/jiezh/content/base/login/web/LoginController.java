package com.jiezh.content.base.login.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.login.service.BaseLoginService;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.Log;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.SpringUtil;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.user.service.BaseUserService;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.content.leads.urge.service.LmurgeService;
import com.jiezh.dao.base.user.UserVO;

/**
 * 登录
 * 
 * @author liangds
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/")
public class LoginController extends WebAction {
    Log log = new Log(LoginController.class);

    @Autowired
    BaseLoginService baseLoginService;
    // add by cj
    @Autowired
    ClientService clientService;
    // add by cj
    @Autowired
    LmurgeService lmurgeService;

    /**
     * 进登录页
     * 
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index() {
        log.info("index");
        return new ModelAndView("base/login/index");
    }

    /**
     * 登录成功页面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("login")
    public ModelAndView login() throws Exception {
        AuthorUser user = getUser();
        if ("0".equals(user.getIsPass())) {
            BaseUserService baseUserService = (BaseUserService) SpringUtil.getBean("base.user.BaseUserService");
            UserVO vo = new UserVO();
            vo.setUserId(getUser().getUserId());
            PageInfo<UserVO> PageInfo = (PageInfo<UserVO>) baseUserService.search(1, vo);
            List<UserVO> list = PageInfo.getList();
            UserVO useVO = (UserVO) list.get(0);
            if ("0".equals(useVO.getIsPass())) { // 密码为必须修改状态
                return Password();
            } else {
                return login(user); // 修改完成后跳转
            }
        } else {
            return login(user); // 登录时直接跳转
        }
    }

    private ModelAndView login(AuthorUser user) throws Exception {
        ModelAndView mv = new ModelAndView("base/login/main");
        // 用户信息
        mv.addObject("_userName_", user.getUserName());
        mv.addObject("_userId_", user.getUserId());
        mv.addObject("_realName_", user.getRealName());
        mv.addObject("_organName_", baseLoginService.getOrganName(user.getOrganId(), user.getOrganCode()));
        mv.addObject("_isRole2_", ("0".equals(checkRole("1")) && "1".equals(checkRole("2"))) ? "1" : "0");// 是否销售 且不是管理员
        // add by houjq
        mv.addObject("_isLock_", user.getIsLock());// 是否锁定
        // 菜单组
        mv.addObject("groupList", baseLoginService.getGroupByUserId(user.getUserId()));

        // add by cj 登陆任务检测
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("user_id", user.getUserId());
        paras.put("month", date);
        mv.addObject("message", clientService.getTaskState(paras));

        String basePath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        mv.addObject("basePath", basePath);
        return mv;
    }

    /**
     * 转换重复提交页面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("repeat")
    public ModelAndView repeat() throws Exception {
        ModelAndView mv = new ModelAndView("pub/403");
        String msg = "重复提交,数据不允许重复提交,如需帮助请联系管理员!!";
        log.info(msg);
        mv.addObject("_repeat_msg_", msg);
        return mv;
    }

    @RequestMapping("Password")
    public ModelAndView Password() throws Exception {
        ModelAndView mv = new ModelAndView("base/user/Password");
        log.info("index");
        BaseUserService baseUserService = (BaseUserService) SpringUtil.getBean("base.user.BaseUserService");
        UserVO vo = new UserVO();
        vo.setUserId(getUser().getUserId());

        PageInfo<UserVO> PageInfo = (PageInfo<UserVO>) baseUserService.search(1, vo);
        List<UserVO> list = PageInfo.getList();

        UserVO user = (UserVO) list.get(0);
        if ("ALL".equals(user.getOrganCode())) {
            user.setOrganName("总公司");
            user.setOrganCompanyName("总公司");
        }
        mv.addObject("authorUser", user);
        return mv;
    }

    // add by cj 首页积分面版
    @RequestMapping("indexPage")
    public ModelAndView indexPage() throws Exception {
        AuthorUser user = getUser();
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("user_id", user.getUserId());
        paras.put("month", date);

        // 不是销售不显示
        if ("0".endsWith(checkRole(Env.ROLE_SALE))) {
            return null;
        }
        // 如果是捷越用户，首页不显示提示信息
        if (user.getOrganId().equals("6868")) {
            return null;
        }
        Map<String, Object> plan = clientService.getPlan(paras);
        // 重要客户查询
        List<Map<String, Object>> impClient = clientService.getTimeoutList(paras);

        // 催促客户查询
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("urgeToPersonId", user.getUserId());
        map.put("urgeStatus", "0");
        List<Map<String, Object>> urgeClient = lmurgeService.selectByLmurgeList(map);

        String totalScore = clientService.getTotalScore(paras);
        ModelAndView mv = new ModelAndView("base/login/index_page");
        mv.addObject("impClient", impClient);
        mv.addObject("urgeClient", urgeClient);
        Object[] score = clientService.getScore(paras);
        // 本月减分
        mv.addObject("subtract", score[0] == null ? "0" : score[0]);
        // 本月加分
        mv.addObject("add", score[1] == null ? "0" : score[1]);
        // 任务量
        mv.addObject("plan_num", plan == null ? "未设置" : plan.get("PLAN_NUM"));
        // 完成数量
        if (plan != null) {
            mv.addObject("perform", plan.get("PERFORM"));
            // 未完成数量
            int num = (Integer.parseInt(plan.get("PLAN_NUM") + "") == 0 ? 0
                : Integer.parseInt(plan.get("PLAN_NUM") + "") - Integer.parseInt(plan.get("PERFORM") + ""));
            mv.addObject("num", num < 0 ? 0 : num);
        }
        // 当月积分
        mv.addObject("sum", totalScore);

        String[] count = clientService.getU_TCount(paras);

        mv.addObject("u_count", count[0]);
        mv.addObject("t_count", count[1]);
        return mv;
    }
}
