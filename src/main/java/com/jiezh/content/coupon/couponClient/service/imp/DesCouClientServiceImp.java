package com.jiezh.content.coupon.couponClient.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.sms.service.SmsService;
import com.jiezh.content.coupon.couponClient.service.DesCouClientService;
import com.jiezh.dao.base.sms.SmsDao;
import com.jiezh.dao.base.sms.SmsSendInfoVO;
import com.jiezh.dao.base.sms.SmsTemplateVO;
import com.jiezh.dao.coupon.client.CouponClientVO;
import com.jiezh.dao.coupon.client.CouponClientVODao;
import com.jiezh.dao.coupon.generate.CouponInfoVODao;

import net.sf.json.JSONObject;

/**
 * 赠券核销service类
 * 
 * @author houjiaqiang
 * @since 2016年7月12日 下午5:12:36
 */
@Service
public class DesCouClientServiceImp implements DesCouClientService {
    private Logger logger = Logger.getLogger(DesCouClientServiceImp.class);
    @Autowired
    CouponClientVODao couponClientVODao;
    @Autowired
    CouponInfoVODao couponInfoVODao;
    @Autowired
    SmsDao smsDao;
    @Autowired
    SmsService smsService;

    @Override
    public String processDestory(String couponCode, AuthorUser user) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponCode", couponCode);
        map.put("userId", user.getUserId());
        int flag = 0;
        try {
            flag = couponInfoVODao.updateByCouponCode(map);
            flag = couponClientVODao.updateByCouponCode(map);
            if (flag == 0) {
                return "不存在输入的赠券码或已经使用！";
            }
        } catch (Exception e) {
            logger.error("核销失败");
            return "核销失败";
        }

        // 发送核销短信
        String msgFlag = processMsg(couponCode, user);
        if (msgFlag.equals("短信发送成功！")) {
            return "核销成功！";
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "核销失败,短信发送失败！";
        }

    }

    public String processMsg(String couponCode, AuthorUser user) {
        String flag = null;
        Map<String, Object> params = new HashMap<>();
        params.put("type", "7");
        SmsTemplateVO template = smsDao.selectSmsTemplateByType(params);
        CouponClientVO clientVO = null;
        SmsSendInfoVO sendInfoVO = null;
        String msg = null;
        Map<String, String> map = new HashMap<>();
        String msgReturn = null;
        params.put("couponCode", couponCode);
        clientVO = couponClientVODao.selectSendInfo(params);
        msg = template.getContent().replace("var1", clientVO.getProduct()).replace("var2", clientVO.getCouponName())
            .replace("var4", clientVO.getPartnerName()).replace("var3", clientVO.getCouponCode());
        map.put("mobile", clientVO.getClientTel());
        map.put("msg", msg);
        try {
            msgReturn = smsService.send("", map);
            JSONObject json = JSONObject.fromObject(msgReturn);
            sendInfoVO = new SmsSendInfoVO();
            sendInfoVO.setMsg(msg);
            sendInfoVO.setReTel(clientVO.getClientTel());
            sendInfoVO.setReName(clientVO.getClientName());
            sendInfoVO.setStoreAddress(clientVO.getAddress());
            sendInfoVO.setStatusDes(msgReturn);
            sendInfoVO.setStatus(json.optString("respstatus"));
            sendInfoVO.setSendUserId(user.getUserId());
            sendInfoVO.setClientId(clientVO.getId());
            sendInfoVO.setCreatedUserId(user.getUserId());
            sendInfoVO.setSmsType("7");
            smsService.saveMsgInfo(sendInfoVO);
        } catch (Exception e) {
            e.printStackTrace();
            flag = "短信未发送，调用短信平台服务出错！";
            logger.error(flag);
        }

        if (flag == null) flag = "短信发送成功！";
        return flag;
    }

}
