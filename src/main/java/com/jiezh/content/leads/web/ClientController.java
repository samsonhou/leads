package com.jiezh.content.leads.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.base.sms.service.SmsService;
import com.jiezh.content.base.user.service.BaseUserService;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.base.sms.SmsSendInfoVO;
import com.jiezh.dao.base.sms.SmsTemplateVO;
import com.jiezh.dao.leads.client.ClientVO;

import net.sf.json.JSONObject;

/**
 * 客户线索管理
 * 
 * @author wp
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/client/")
public class ClientController extends WebAction {
    Logger logger = Logger.getLogger(ClientController.class);
    @Autowired
    ClientService clientService;

    @Autowired
    SmsService smsService;

    @Autowired
    BaseUserService baseUserService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/clientlist");
        Page<ClientVO> page = new Page<ClientVO>();
        mv.addObject("page", new PageInfo<ClientVO>(page));
        mv.addObject("clientVO", new ClientVO());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/clientlist");

        ClientVO clientVO = (ClientVO) getBean(ClientVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null || !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        AuthorUser user = getUser();
        clientVO.setRid(user.getUserId().intValue());
        clientVO.setSysOrganCode(user.getOrganCode());

        mv.addObject("page", clientService.getClientList(currenPage, clientVO));
        mv.addObject("clientVO", clientVO);
        return mv;
    }

    @RequestMapping("addClient")
    public ModelAndView addClient() throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/addClient");
        mv.addObject("clientVO", new ClientVO());
        // 业务员加的客户，只能分配给自己。是1.。。客服可以分配给其他人。2 修改3
        mv.addObject("isnew", "2");
        return mv;
    }

    @RequestMapping("updateClient")
    public ModelAndView updateClient() throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/addClient");

        String clID = request.getParameter("id");

        ClientVO clientVO = clientService.searchVo(clID);
        String dealPerson = clientService.searchPerson(clID);

        mv.addObject("clientVO", clientVO);
        // 业务员加的客户，只能分配给自己。是1.。。客服可以分配给其他人。2 修改3
        mv.addObject("isnew", "3");
        // 修改的时候加入处理人的名字
        mv.addObject("dealPerson", dealPerson);
        return mv;
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
     * @param bigPid
     * @throws Exception
     *             异步校验电话
     */
    @RequestMapping(value = "checkTel")
    @ResponseBody
    public String checkTel(String tel) throws Exception {

        String reString = clientService.checkTel(tel);

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

        String from = request.getParameter("from");
        String str0 = clientService.queryOrgPerson(from, user.getOrganId(), user.getUserId(), user.getOrganCode());

        response.getWriter().print(str0);
        // response.getWriter().print(str0 );

        return null;
    }

    /**
     * @param bigPid
     * @throws Exception
     *             保存
     */
    @RequestMapping(value = "saveClient")
    @ResponseBody
    public ModelAndView saveClient(String isnew) throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/addClient");
        AuthorUser user = getUser();
        ClientVO clientVO = (ClientVO) getBean(ClientVO.class);

        // modify bj cj
        if (clientVO.getSid() > 0) {
            clientVO.setAllotdate(new Date());
            clientService.insertAllocationItem(new String[] {clientVO.getSid() + ""}, user.getUserId(), clientVO.getSid());

        } else {
            clientVO.setAllotdate(null);
        }

        if ("1".equals(isnew) || "2".equals(isnew)) {
            clientVO.setRid(user.getUserId().intValue());
            clientVO.setCityid(user.getOrganCode());
            clientService.addClient(clientVO);
        } else clientService.updateClient(clientVO,user);

        try {
            // 发送短信
            if (clientVO.getSid() > 0 && ("1".equals(isnew) || "2".equals(isnew)) && StringUtils.isNotBlank(clientVO.getTel())) {
                Map<String, Object> params = new HashMap<>();
                params.put("userId", clientVO.getSid());
                Map<String, Object> result = baseUserService.getSmsInfo(params);
                String tel = result.get("TEL") == null ? null : result.get("TEL").toString();
                String address = result.get("ADDRESS") == null ? null : result.get("ADDRESS").toString();
                if (StringUtils.isNotBlank(tel)) {
                    SmsTemplateVO smsTemplateVO = null;
                    SmsSendInfoVO sendInfoVO = new SmsSendInfoVO();
                    sendInfoVO.setCreatedUserId(user.getUserId());
                    Map<String, String> map = new HashMap<>();
                    if (clientVO.getFromtype() == 391 && StringUtils.isNotBlank(address)) {
                        // message to client
                        params.put("type", "1");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        String msg =
                            smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel).replace("var3", address);
                        map.put("mobile", clientVO.getTel());
                        map.put("msg", msg);
                        String msgReturn = smsService.send("", map);
                        JSONObject json = JSONObject.fromObject(msgReturn);
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(clientVO.getTel());
                        sendInfoVO.setReName(clientVO.getClientName());
                        sendInfoVO.setStoreAddress(address);
                        sendInfoVO.setStatusDes(msgReturn);
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        sendInfoVO.setClientId(clientVO.getId());
                        smsService.saveMsgInfo(sendInfoVO);
                        // message to salesman
                        params.put("type", "3");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel());
                        map.put("mobile", tel);
                        map.put("msg", msg);
//                        msgReturn = smsService.messageSend(tel, msg, "", "", "", "");
                        msgReturn = smsService.send("", map);
                        json = JSONObject.fromObject(msgReturn);
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(tel);
                        sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                        sendInfoVO.setStatusDes(msgReturn);
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        sendInfoVO.setClientId(clientVO.getId());
                        smsService.saveMsgInfo(sendInfoVO);
                    } else if (clientVO.getFromtype() == 391 && StringUtils.isBlank(address)) {
                        // message to client
                        params.put("type", "2");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel);
                        map.put("mobile", clientVO.getTel());
                        map.put("msg", msg);
                        String msgReturn = smsService.send("", map);
                        JSONObject json = JSONObject.fromObject(msgReturn);
                        //String msgReturn = smsService.messageSend(clientVO.getTel(), msg, "", "", "", "");
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(clientVO.getTel());
                        sendInfoVO.setReName(clientVO.getClientName());
                        sendInfoVO.setStoreAddress(address);
                        sendInfoVO.setStatusDes(msgReturn);
//                        sendInfoVO.setStatus(msgReturn.substring(msgReturn.lastIndexOf("=") + 1));
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        smsService.saveMsgInfo(sendInfoVO);
                        // message to salesman
                        params.put("type", "3");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel());
                        map.put("mobile", clientVO.getTel());
                        map.put("msg", msg);
//                        msgReturn = smsService.messageSend(tel, msg, "", "", "", "");
                        msgReturn = smsService.send("", map);
                        json = JSONObject.fromObject(msgReturn);
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(tel);
                        sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                        sendInfoVO.setStatusDes(msgReturn);
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        sendInfoVO.setClientId(clientVO.getId());
                        smsService.saveMsgInfo(sendInfoVO);
                    } else if (clientVO.getFromtype() != 391 && StringUtils.isNotBlank(address)) {
                        // message to client
                        params.put("type", "4");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        String msg =
                            smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel).replace("var3", address);
                        //String msgReturn = smsService.messageSend(clientVO.getTel(), msg, "", "", "", "");
                        map.put("mobile", clientVO.getTel());
                        map.put("msg", msg);
                        String msgReturn = smsService.send("", map);
                        JSONObject json = JSONObject.fromObject(msgReturn);
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(clientVO.getTel());
                        sendInfoVO.setReName(clientVO.getClientName());
                        sendInfoVO.setStoreAddress(address);
                        sendInfoVO.setStatusDes(msgReturn);
//                        sendInfoVO.setStatus(msgReturn.substring(msgReturn.lastIndexOf("=") + 1));
                        sendInfoVO.setStatus(msgReturn.substring(msgReturn.lastIndexOf("=") + 1));
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        sendInfoVO.setClientId(clientVO.getId());
                        smsService.saveMsgInfo(sendInfoVO);
                    } else if (clientVO.getFromtype() != 391 && StringUtils.isBlank(address)) {
                        // message to client
                        params.put("type", "5");
                        smsTemplateVO = smsService.getMsgTemplate(params);
                        String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel);
//                        String msgReturn = smsService.messageSend(clientVO.getTel(), msg, "", "", "", "");
                        map.put("mobile", clientVO.getTel());
                        map.put("msg", msg);
                        String msgReturn = smsService.send("", map);
                        JSONObject json = JSONObject.fromObject(msgReturn);
                        sendInfoVO.setMsg(msg);
                        sendInfoVO.setReTel(clientVO.getTel());
                        sendInfoVO.setReName(clientVO.getClientName());
                        sendInfoVO.setStatusDes(msgReturn);
//                        sendInfoVO.setStatus(msgReturn.substring(msgReturn.lastIndexOf("=") + 1));
                        sendInfoVO.setStatus(json.optString("respstatus"));
                        sendInfoVO.setSendUserId(user.getUserId());
                        sendInfoVO.setClientId(clientVO.getId());
                        smsService.saveMsgInfo(sendInfoVO);
                    }
                    mv.addObject("message", "已保存成功并已发送短信提醒!");
                    return mv;
                }
            }
        } catch (Exception e) {
            logger.error("发送短信失败！！------------------" + e.getMessage());
            mv.addObject("message", "已保存成功但发送短信失败!");
            return mv;
        }

        mv.addObject("message", "已保存成功!");
        return mv;
    }

    /**
     * 催促
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "hasten")
    @ResponseBody
    public void hasten(String taskId) throws Exception {
        response.getWriter().write(clientService.hastenTask(taskId, getUser()).get("msg"));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
