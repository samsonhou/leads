/**
 * 
 */
package com.jiezh.content.base.sms.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.sms.service.SmsService;

/**
 * 发送短信controller类
 * 
 * @author houjiaqiang
 * @since 2016年6月23日 上午9:46:44
 */
@Controller
@RequestMapping("/base/sms/")
public class SmsController extends WebAction {

    @Autowired
    SmsService smsService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("/base/sms/smslist");
        mv.addObject("orgId", getUser().getOrganId());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("/base/sms/smslist");
        String orgId = getUser().getOrganId();

        String clientName = request.getParameter("clientName");
        String clientTel = request.getParameter("clientTel");
        String organId = request.getParameter("organId") == null ? orgId : request.getParameter("organId");
        String sendStatus = request.getParameter("sendStatus");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("clientName", clientName);
        params.put("clientTel", clientTel);
        params.put("organId", organId);
        params.put("sendStatus", sendStatus);
        params.put("startDate", startDate);
        params.put("endDate", endDate);

        mv.addObject("orgId", orgId);
        mv.addObject("clientName", clientName);
        mv.addObject("clientTel", clientTel);
        mv.addObject("organId", organId);
        mv.addObject("sendStatus", sendStatus);
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("page", smsService.queryClient(currenPage, params));
        return mv;
    }
    
    @RequestMapping("sendMsg")
    @ResponseBody
    public Map<String, String> sendMsg() throws Exception{
        String clientId = request.getParameter("id");
        Map<String, String> map = new HashMap<>();
        map.put("msg", smsService.processMsg(clientId, getUser()));
        return map;
    }
}
