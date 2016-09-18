package com.jiezh.content.leads.assign.web;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.token.Token;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.leads.activity.ActivityConfigVO;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 分配
 * 
 * @author liangds
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/assign")
public class AssignController extends WebAction {
    @Autowired
    ClientService clientService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/list");
        Page<ClientVO> page = new Page<ClientVO>();
        mv.addObject("page", new PageInfo<ClientVO>(page));
        mv.addObject("clientVO", new ClientVO());

        String flag = ""; // add by cj 判断是否是管理员 0是 1否
        AuthorUser user = getUser();
        if ("1".endsWith(checkRole(Env.ROLE_MANAGE)) && user.getOrganId().equals("00")) {
            // add by cj
            // 管理员&总公司
            flag = "0";
        } else {
            // add by cj
            flag = "1";
        }
        mv.addObject("flag", flag);
        return mv;
    }

    // add by cj
    @RequestMapping("delData")
    public ModelAndView delData() throws Exception {
        String[] ids = request.getParameter("ids").split(",");
        clientService.delData(Arrays.asList(ids));
        return queryList();
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/list");
        ClientVO clientVO = (ClientVO) getBean(ClientVO.class);

        String flag = ""; // add by cj 判断是否是管理员 0是 1否
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        AuthorUser user = getUser();
        clientVO.setRid(user.getUserId().intValue());
        clientVO.setSysOrganCode(user.getOrganCode());
        // 是否管理员 管理员查询它所在机构下所有的
        if ("1".endsWith(checkRole(Env.ROLE_MANAGE))) {
            clientVO.setCompanyid(user.getOrganId());
            // 需要查询自己录入的。未分配的线索。所以借用rid查询
            clientVO.setRid(user.getUserId().intValue());

            mv.addObject("page", clientService.getAssignByManager(currenPage, clientVO));
            if (user.getOrganId().equals("00")) {
                // add by cj
                flag = "0";
            }
        } else {
            mv.addObject("page", clientService.getAssignList(currenPage, clientVO));

            // add by cj
            flag = "1";
        }
        String dealPerson = request.getParameter("sidName");

        mv.addObject("dealPerson", dealPerson);
        mv.addObject("clientVO", clientVO);
        mv.addObject("flag", flag);
        return mv;
    }

    @Token(generate = true)
    @RequestMapping("toAssgin")
    public ModelAndView toAssginPage() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/info");
        String assginItems = request.getParameter("assginItems");
        if (assginItems == null || "".equals(assginItems)) {
            assginItems = "-100";
        }
        mv.addObject("list", clientService.getClientAssginTask(Arrays.asList(assginItems.split(","))));
        return mv;
    }

    @Token(validate = true)
    @RequestMapping("assgin")
    public ModelAndView assgin() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/info");
        String ids[] = request.getParameterValues("clientId");
        String newUserId = request.getParameter("newUserId");
        String newUserOrganId = request.getParameter("newUserOrganId");
        String newUserCode = request.getParameter("newUserCode");
        int count = clientService.updateTaskUser(Arrays.asList(ids), newUserId, newUserOrganId);
        mv.addObject("message", "已将" + count + "条线索分配给" + newUserCode);

        // add by cj 记录分配信息
        AuthorUser user = getUser();
        clientService.insertAllocationItem(ids, user.getUserId(), newUserId);
        return mv;
    }

    @RequestMapping("addTrace")
    @ResponseBody
    public ModelAndView addTrace() throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/addTrace");
        String clID = request.getParameter("id");

        ClientVO clientVO = new ClientVO();
        clientVO.setId(Long.parseLong(clID));

        mv.addObject("cMap", clientService.searchClient(clientVO));

        mv.addObject("trace", clientService.searchTrace(clientVO));

        mv.addObject("serviceTrace", clientService.getServiceTrace(clientVO));

        mv.addObject("fromwhere", "viewonly");

        ActivityConfigVO activityConfigVO = clientService.getActivityConf(Long.valueOf(1));
        mv.addObject("actConfig", activityConfigVO);
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")
            || (activityConfigVO.getOrgans().indexOf(user.getOrganId()) > -1 && new Date().getTime() <= activityConfigVO.getEndDate().getTime())) {
            mv.addObject("flag", "Y");
        }
        return mv;
    }
}
