package com.jiezh.content.leads.handover.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.handover.service.HandoverService;

/**
 * 工作交接controller类
 * 
 * @author houjiaqiang
 * @since 2016年10月8日 下午1:39:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/handover/")
public class HandoverController extends WebAction {

    @Autowired
    HandoverService handoverService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("leads/handover/list");
        return mv;
    }

    /**
     * 
     * 处理工作交接
     * 
     * @param
     * @return
     *         Exception
     */
    @RequestMapping("dealHandover")
    @ResponseBody
    public String handover() throws Exception {
        String sid = request.getParameter("sid");
        String companyid = request.getParameter("companyid");
        Map<String, Object> param = new HashMap<>();
        param.put("sid", sid);
        param.put("companyid", companyid);
        String msg = handoverService.processHandover(param).toString();
        return msg;
    }
}
