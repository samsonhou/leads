package com.jiezh.content.base.sms.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.sms.service.SmsService;

@Controller
@RequestMapping("/base/csms/")
public class CustomSmsController extends WebAction {

    public Log log = LogFactory.getLog(CustomSmsController.class);

    @Autowired
    SmsService smsService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("/base/sms/csmslist");
        mv.addObject("orgId", getUser().getOrganId());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("/base/sms/csmslist");
        String orgId = getUser().getOrganId();

        String clientName = request.getParameter("clientName");
        String clientTel = request.getParameter("clientTel");
        String organId = request.getParameter("organId") == null ? orgId : request.getParameter("organId");
        String sendStatus = request.getParameter("sendStatus");

        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }

        Map<String, Object> params = new HashMap<>();
        params.put("clientName", clientName);
        params.put("clientTel", clientTel);
        params.put("organId", organId);
        params.put("sendStatus", sendStatus);

        mv.addObject("orgId", orgId);
        mv.addObject("clientName", clientName);
        mv.addObject("clientTel", clientTel);
        mv.addObject("organId", organId);
        mv.addObject("sendStatus", sendStatus);
        mv.addObject("page", smsService.querySendMsgList(currenPage, params));
        return mv;
    }

    @RequestMapping("sendMsg")
    @ResponseBody
    public Map<String, String> sendMsg() {
        Map<String, String> map = new HashMap<>();
        try {
            map.put("id", request.getParameter("id"));
            map.put("mobile", request.getParameter("tel"));
            map.put("msg", request.getParameter("msg"));
            int flag = smsService.send(map, getUser());
            map.clear();
            map.put("msg", flag == 0 ? "发送成功!" : "发送失败！");
        } catch (Exception e) {
            map.put("msg", "短信发送时出现错误，请稍后重新发送！");
        }
        return map;
    }

}
