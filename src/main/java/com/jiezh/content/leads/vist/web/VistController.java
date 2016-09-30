package com.jiezh.content.leads.vist.web;

import java.util.Date;
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
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.content.leads.urge.service.LmurgeService;
import com.jiezh.dao.leads.activity.ActivityConfigVO;
import com.jiezh.dao.leads.client.ClientTraceVO;
import com.jiezh.dao.leads.client.ClientVO;

/**
 * 跟踪回复
 * 
 * @author wp
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/vist")
public class VistController extends WebAction {

    @Autowired
    ClientService clientService;

    @Autowired
    LmurgeService lmurgeService; // 线索催促

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/clientlist");
        Page<ClientVO> page = new Page<ClientVO>();
        mv.addObject("page", new PageInfo<ClientVO>(page));
        mv.addObject("clientVO", new ClientVO());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/clientlist");
        ClientVO clientVO = (ClientVO) getBean(ClientVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }

        AuthorUser user = getUser();
        clientVO.setSid(user.getUserId().intValue());
        clientVO.setSysOrganCode(user.getOrganCode());
        mv.addObject("page", clientService.getVistList(currenPage, clientVO));
        mv.addObject("clientVO", clientVO);
        return mv;
    }

    @RequestMapping("addClient")
    public ModelAndView addClient() throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/addClient");
        mv.addObject("clientVO", new ClientVO());
        // 业务员加的客户，只能分配给自己。是1.。。客服可以分配给其他人。2
        AuthorUser user = getUser();
        mv.addObject("sidFromye", user.getUserId());
        mv.addObject("sidNameFromye", user.getRealName());
        mv.addObject("companyidFromye", user.getOrganId());
        mv.addObject("isnew", "1");
        return mv;
    }

    @RequestMapping("updateClient")
    public ModelAndView detail() throws Exception {

        return new ModelAndView("redirect:addClient");
    }

    /**
     * @param bigPid
     * @throws Exception
     *             异步查询数结构
     */
    @RequestMapping(value = "querySub")
    @ResponseBody
    public String querySub(String bigPid) throws Exception {

        String reString = clientService.querySub(bigPid);

        response.getWriter().print(reString);
        return null;
    }

    /**
     * @throws Exception
     *             查询机构和人的数
     */
    @RequestMapping(value = "queryOrgPerson")
    @ResponseBody
    public String queryOrgPerson() throws Exception {
        AuthorUser user = getUser();
        String str0 = clientService.queryOrgPerson("", user.getOrganId(), user.getUserId(), user.getOrganCode());
        response.getWriter().print(str0);
        // response.getWriter().print(str0 );

        return null;
    }

    /**
     * @throws Exception
     *             保存
     */
    @RequestMapping(value = "saveTrace")
    public ModelAndView saveTrace(@RequestParam(value = "urgeid", required = true, defaultValue = "0") long urgeid) throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/addTrace");
        AuthorUser user = getUser();

        VistBean vistBean = new VistBean();
        vistBean.getClientVO(request);
        vistBean.getClientTraceVO(user, request);
        vistBean.setUrgeid(urgeid);

        String msg = clientService.processClient(user, vistBean);

        mv.addObject("comform", "0");
        mv.addObject("message", msg);
        return mv;
    }

    /**
     * @throws Exception
     *             保存
     */
    @RequestMapping(value = "saveClientAndTrace")
    @ResponseBody
    public Map<String, String> saveClientAndTrace() throws Exception {
        AuthorUser user = getUser();
        ClientVO clientVo = (ClientVO) this.getBean(ClientVO.class);
        String fromtype = request.getParameter("code");
        clientVo.setFromtype(Integer.valueOf(fromtype));
        ClientTraceVO clientTraceVo = (ClientTraceVO) this.getBean(ClientTraceVO.class);
        String msg = clientService.processClientAndTrace(user, clientVo, clientTraceVo);
        Map<String, String> map = new HashMap<>();
        map.put("status", "y");
        map.put("info", "保存成功！");
        return map;
    }

    @RequestMapping("addTrace")
    @ResponseBody
    public ModelAndView addTrace(@RequestParam(value = "urgeid", required = true, defaultValue = "0") String urgeid) throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/addTrace");
        String clID = request.getParameter("id");
        ClientVO clientVO = new ClientVO();
        clientVO.setId(Long.parseLong(clID));
        mv.addObject("cMap", clientService.searchClient(clientVO));
        mv.addObject("trace", clientService.searchTrace(clientVO));
        mv.addObject("serviceTrace", clientService.getServiceTrace(clientVO));
        // 来源标识 add by liangds 2016-01-19
        String comform = request.getParameter("comform");
        if ("1".equals(comform)) {
            comform = "1";
        } else {
            comform = "0";
        }
        mv.addObject("urgeid", urgeid); // 传递催促ID
        mv.addObject("comform", comform);

        ActivityConfigVO activityConfigVO = clientService.getActivityConf(Long.valueOf(1));
        mv.addObject("actConfig", activityConfigVO);
        AuthorUser user = getUser();
        if (user.getOrganId().equals("00")
            || (activityConfigVO.getOrgans().indexOf(user.getOrganId()) > -1 && new Date().getTime() <= activityConfigVO.getEndDate().getTime())) {
            mv.addObject("flag", "Y");
        }

        return mv;
    }

    /**
     * 进件
     * 
     * @throws Exception
     */
    @RequestMapping(value = "toERP")
    public ModelAndView toERP() throws Exception {
        ModelAndView mv = new ModelAndView("leads/vist/addTrace");
        AuthorUser user = getUser();
        ClientVO vo = (ClientVO) getBean(ClientVO.class);
        
        String msg = clientService.processToErp(user, vo);

        mv.addObject("comform", "0");
        mv.addObject("message", msg);
        return mv;
    }

}
