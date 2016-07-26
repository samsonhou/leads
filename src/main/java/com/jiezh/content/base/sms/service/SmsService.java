/**
 * 
 */
package com.jiezh.content.base.sms.service;

import java.util.Map;

import org.apache.axis2.AxisFault;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.sms.SmsConfigVO;
import com.jiezh.dao.base.sms.SmsSendInfoVO;
import com.jiezh.dao.base.sms.SmsTemplateVO;

/**
 * 短信发送service接口
 * 
 * @author houjiaqiang
 * @since 2016年5月25日 下午3:49:59
 */
public interface SmsService {
    /**
     * 
     * 短信发送
     * 
     * @param sdst 电话英文,分割 smsg 短信内容 stime 定时 sexno 扩展号 islong 是否长信息 sequnceId 发送短信标识
     * @return
     *         Exception
     */
    public String messageSend(String sdst, String smsg, String stime, String sexno, String islong, String sequnceId) throws AxisFault;

    /**
     * 
     * 获取短信模板
     * 
     * @param
     * @return
     *         Exception
     */
    public SmsTemplateVO getMsgTemplate(Map<String, Object> params);

    /**
     * 
     * 保存短信内容
     * 
     * @param
     * @return
     *         Exception
     */
    public int saveMsgInfo(SmsSendInfoVO record);

    public SmsConfigVO getPlatFormInfo();

    public String send(String url, Map<String, String> map) throws Exception;

    public PageInfo<Map<String, Object>> queryClient(int currentPage, Map<String, Object> params);
    
    public String processMsg(String id, AuthorUser user);
}
