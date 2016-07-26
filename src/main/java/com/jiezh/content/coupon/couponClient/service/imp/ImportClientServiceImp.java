package com.jiezh.content.coupon.couponClient.service.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.ExcelImportUtil;
import com.jiezh.content.base.sms.service.SmsService;
import com.jiezh.content.coupon.couponClient.service.ImportClientService;
import com.jiezh.dao.base.sms.SmsDao;
import com.jiezh.dao.base.sms.SmsSendInfoVO;
import com.jiezh.dao.base.sms.SmsTemplateVO;
import com.jiezh.dao.coupon.client.CouponClientVO;
import com.jiezh.dao.coupon.client.CouponClientVODao;
import com.jiezh.dao.coupon.generate.CouponInfoVO;
import com.jiezh.dao.coupon.generate.CouponInfoVODao;

import net.sf.json.JSONObject;

/**
 * 客户导入service类
 * 
 * @author houjiaqiang
 * @since 2016年7月8日 上午10:48:19
 */
@Service
public class ImportClientServiceImp implements ImportClientService {

    @Autowired
    CouponClientVODao couponClientVODao;
    @Autowired
    CouponInfoVODao couponInfoVODao;
    @Autowired
    SmsDao smsDao;
    @Autowired
    SmsService smsService;

    @Override
    public String processImportClient(MultipartFile file, AuthorUser user) throws Exception {
        String error = "";

        ExcelImportUtil excelUtil = new ExcelImportUtil();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("xlsStartRow", "1");
        paramMap.put("xlsStartCol", "0");
        paramMap.put("xlsSheet", "0");
        paramMap.put("xlsMaxRow", "10000");
        paramMap.put("xlsIsEmptyRow", "0");
        List<Object[]> list = excelUtil.importContentFromExcelInputStream(file.getInputStream(), paramMap, file.getOriginalFilename());

        for (int i = 0; i < list.size(); i++) {
            CouponClientVO clientVO = new CouponClientVO();
            Object[] obj = (Object[]) list.get(i);
            if (obj[0] == null || obj[0].toString().equals("")) {
                error += "第" + (i + 1) + "行客户姓名为空，";
            } else {
                clientVO.setClientName(obj[0].toString());
            }
            if (obj[1] == null || obj[1].toString().equals("")) {
                error += "第" + (i + 1) + "行手机号为空，";
            } else {
                clientVO.setClientTel(obj[1].toString());
            }
            if (obj[2] == null || obj[2].toString().equals("")) {
                error += "第" + (i + 1) + "行合作商编码为空，";
            } else {
                clientVO.setPartnerCode(obj[2].toString());
            }
            if (obj[3] == null || obj[3].toString().equals("")) {
                error += "第" + (i + 1) + "行券类型为空，";
            } else {
                clientVO.setCouponName(obj[3].toString());
            }
            if (obj[4] == null || obj[4].toString().equals("")) {
                error += "第" + (i + 1) + "行面值为空，";
            } else {
                clientVO.setValue(new BigDecimal(obj[4].toString()));
            }
            if (obj[5] == null || obj[5].toString().equals("")) {
                error += "第" + (i + 1) + "行数量为空，";
            } else {
                clientVO.setCount(Integer.valueOf(obj[5].toString()));
            }
            if (obj[6] == null || obj[6].toString().equals("")) {
                error += "第" + (i + 1) + "行购买产品为空，";
            } else {
                clientVO.setProduct(obj[6].toString());
            }

            if (StringUtils.isNotBlank(error)) {
                continue;
            } else {
                String msg = checkCoupon(clientVO);
                if (msg.equals("success")) {
                    clientVO.setCreatedUserId(user.getUserId());
                    couponClientVODao.insertByImport(clientVO);
                    couponInfoVODao.updateByImport(clientVO);
                } else {
                    error += "第" + (i + 1) + "行" + msg + ",";
                }

            }
        }
        if (StringUtils.isNotBlank(error)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return error;
    }

    @Override
    public PageInfo<CouponClientVO> getcouponClient(CouponClientVO record, int curPage) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<CouponClientVO> page = (Page<CouponClientVO>) couponClientVODao.selectByVO(record);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<>(page);
    }

    public String checkCoupon(CouponClientVO clientVO) {
        List<Map<String, Object>> list = couponInfoVODao.selectByCheck(clientVO);
        String msg = "";
        for (Map<String, Object> map : list) {
            msg += map.get("MSG");
        }
        return msg;
    }

    @Override
    public String processMsg(String ids, AuthorUser user) {
        String flag = null;
        String[] id = ids.split(",");
        Map<String, Object> params = new HashMap<>();
        params.put("type", "6");
        SmsTemplateVO template = smsDao.selectSmsTemplateByType(params);
        CouponClientVO clientVO = null;
        SmsSendInfoVO sendInfoVO = null;
        String msg = null;
        Map<String, String> map = new HashMap<>();
        String msgReturn = null;
        for (String str : id) {
            params.put("id", Long.valueOf(str));
            clientVO = couponClientVODao.selectSendInfo(params);
            msg = template.getContent().replace("var1", clientVO.getClientName()).replace("var2", clientVO.getProduct())
                .replace("var3", clientVO.getPartnerName()).replace("var4", clientVO.getCouponCode()).replace("var5", clientVO.getAddress());
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
                sendInfoVO.setSmsType("6");
                smsService.saveMsgInfo(sendInfoVO);
                CouponClientVO record = new CouponClientVO();
                record.setId(clientVO.getId());
                record.setStatus("2");
                record.setUpdatedTime(new Date());
                record.setSendDate(new Date());
                couponClientVODao.updateByPrimaryKeySelective(record);
                CouponInfoVO infoVO = new CouponInfoVO();
                infoVO.setId(clientVO.getCouponId());
                infoVO.setStatus("2");
                couponInfoVODao.updateByPrimaryKeySelective(infoVO);
            } catch (Exception e) {
                e.printStackTrace();
                flag = "短信未发送，调用短信平台服务出错！";
            }

        }
        if (flag == null) flag = "短信发送成功！";
        return flag;
    }

}
