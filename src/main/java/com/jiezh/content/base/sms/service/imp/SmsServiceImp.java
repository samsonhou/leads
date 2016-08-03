package com.jiezh.content.base.sms.service.imp;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
// import org.apache.cxf.endpoint.Client;
// import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.sms.service.SmsService;
import com.jiezh.dao.base.sms.SmsConfigVO;
import com.jiezh.dao.base.sms.SmsDao;
import com.jiezh.dao.base.sms.SmsSendInfoVO;
import com.jiezh.dao.base.sms.SmsTemplateVO;
import com.jiezh.dao.base.user.BaseUserDao;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.client.ClientVO;

import net.sf.json.JSONObject;

/**
 * 短信发送service实现类
 * 
 * @author houjiaqiang
 * @since 2016年5月25日 下午3:52:13
 */
@Service
public class SmsServiceImp implements SmsService {
    Logger logger = Logger.getLogger(SmsServiceImp.class);
    @Autowired
    private SmsDao smsDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private BaseUserDao baseUserDao;

    @Override
    public String messageSend(String sdst, String smsg, String stime, String sexno, String islong, String sequnceId) throws AxisFault {

        RPCServiceClient client = null;
        Object[] result = null;
        SmsConfigVO configVO = getPlatFormInfo();
        client = new RPCServiceClient();
        Options options = client.getOptions();
        // 创建一个远程的访问地址
        EndpointReference target = new EndpointReference(configVO.getUrl());
        options.setTo(target);

        Object[] objargs = new Object[] {configVO.getUserName(), configVO.getUserPwd(), sdst, smsg, stime, sexno, islong, sequnceId};
        Class<?>[] getobj = new Class[] {String.class};
        result = client.invokeBlocking(new QName("http://webservice.www.smsweb.com", "Masssend1"), objargs, getobj);
        logger.info(result[0].toString());

        return result[0].toString();

        // SmsConfigVO configVO = getPlatFormInfo();
        // JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        // Client client = factory.createClient(configVO.getUrl());
        // Object[] result = null;
        // try {
        // result = client.invoke("Masssend1", configVO.getUserName(), configVO.getUserPwd(), sdst, smsg, stime, sexno, islong, sequnceId);
        // logger.info(result[0].toString());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return result[0].toString();
    }

    public SmsConfigVO getPlatFormInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("smsName", "lxt");
        return smsDao.selectSmsConfigBySmsName(params);
    }

    @Override
    public SmsTemplateVO getMsgTemplate(Map<String, Object> params) {
        return smsDao.selectSmsTemplateByType(params);
    }

    @Override
    public int saveMsgInfo(SmsSendInfoVO record) {
        return smsDao.insertSmsSendInfo(record);
    }

    /**
     * 
     * @param url
     *            应用地址，类似于http://ip:port/sms/send
     * @param map
     *            参数列表
     * @return 返回值定义参见协议文档
     * @throws Exception
     */
    public String send(String url, Map<String, String> map) throws Exception {
        SmsConfigVO configVO = getPlatFormInfo();
        url = configVO.getUrl();
        map.put("name", configVO.getUserName());
        map.put("pswd", configVO.getUserPwd());

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            params.add(new BasicNameValuePair(name, map.get(name)));
        }

        httppost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    }

    @Override
    public PageInfo<Map<String, Object>> queryClient(int currentPage, Map<String, Object> params) {
        PageHelper.startPage(currentPage, Env.PAGE_SIZE);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) smsDao.selectClient(params);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);
    }

    public String processMsg(String id, AuthorUser user) {
        ClientVO clientVO = clientDao.selectByPrimaryKey(Long.valueOf(id));
        String flag = "";
        try {
            // 发送短信
            Map<String, Object> params = new HashMap<>();
            params.put("userId", clientVO.getSid());
            Map<String, Object> result = baseUserDao.selectSmsInfo(params);
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
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel).replace("var3", address);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStoreAddress(address);
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                    // message to salesman
                    params.put("type", "3");
                    smsTemplateVO = getMsgTemplate(params);
                    msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel());
                    map.put("mobile", tel);
                    map.put("msg", msg);
                    msgReturn = send("", map);
                    json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(tel);
                    sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                } else if (clientVO.getFromtype() == 391 && StringUtils.isBlank(address)) {
                    // message to client
                    params.put("type", "2");
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStoreAddress(address);
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                    // message to salesman
                    params.put("type", "3");
                    smsTemplateVO = getMsgTemplate(params);
                    msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel());
                    map.put("mobile", tel);
                    map.put("msg", msg);
                    msgReturn = send("", map);
                    json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(tel);
                    sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                } else if (clientVO.getFromtype() == 550 && StringUtils.isNotBlank(address)) {
                    // message to client
                    params.put("type", "8");
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel).replace("var3", address);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStoreAddress(address);
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                    // message to salesman
                    params.put("type", "10");
                    smsTemplateVO = getMsgTemplate(params);
                    msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel()).replace("var3",
                        new SimpleDateFormat("HH:mm").format(clientVO.getQdate()));
                    map.put("mobile", tel);
                    map.put("msg", msg);
                    msgReturn = send("", map);
                    json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(tel);
                    sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                } else if (clientVO.getFromtype() == 550 && StringUtils.isBlank(address)) {
                    // message to client
                    params.put("type", "9");
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStoreAddress(address);
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                    // message to salesman
                    params.put("type", "10");
                    smsTemplateVO = getMsgTemplate(params);
                    msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getTel()).replace("var3",
                        new SimpleDateFormat("HH:mm").format(clientVO.getQdate()));
                    map.put("mobile", tel);
                    map.put("msg", msg);
                    msgReturn = send("", map);
                    json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(tel);
                    sendInfoVO.setReName(result.get("NAME") == null ? "" : result.get("NAME").toString());
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                } else if (clientVO.getFromtype() != 391 && clientVO.getFromtype() != 550 && StringUtils.isNotBlank(address)) {
                    // message to client
                    params.put("type", "4");
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel).replace("var3", address);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStoreAddress(address);
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(msgReturn.substring(msgReturn.lastIndexOf("=") + 1));
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                } else if (clientVO.getFromtype() != 391 && clientVO.getFromtype() != 550 && StringUtils.isBlank(address)) {
                    // message to client
                    params.put("type", "5");
                    smsTemplateVO = getMsgTemplate(params);
                    String msg = smsTemplateVO.getContent().replace("var1", clientVO.getClientName()).replace("var2", tel);
                    map.put("mobile", clientVO.getTel());
                    map.put("msg", msg);
                    String msgReturn = send("", map);
                    JSONObject json = JSONObject.fromObject(msgReturn);
                    sendInfoVO.setMsg(msg);
                    sendInfoVO.setReTel(clientVO.getTel());
                    sendInfoVO.setReName(clientVO.getClientName());
                    sendInfoVO.setStatusDes(msgReturn);
                    sendInfoVO.setStatus(json.optString("respstatus"));
                    sendInfoVO.setSendUserId(user.getUserId());
                    sendInfoVO.setClientId(clientVO.getId());
                    saveMsgInfo(sendInfoVO);
                }

                flag = "短信发送成功！";
            } else {
                flag = "短信未发送，系统中不存在客户经理手机号！";
            }
        } catch (Exception e) {
            flag = "短信未发送，调用短信平台服务出错！";
        }
        return flag;
    }

}
