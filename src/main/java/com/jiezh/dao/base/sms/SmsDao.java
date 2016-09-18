/**
 * 
 */
package com.jiezh.dao.base.sms;

import java.util.List;
import java.util.Map;

/**
 * 短信发送Dao
 * 
 * @author houjiaqiang
 * @since 2016年5月25日 下午5:37:39
 */
public interface SmsDao {

    SmsConfigVO selectSmsConfigBySmsName(Map<String, Object> params);

    SmsTemplateVO selectSmsTemplateByType(Map<String, Object> params);

    int insertSmsSendInfo(SmsSendInfoVO record);

    List<Map<String, Object>> selectClient(Map<String, Object> params);

    List<Map<String, Object>> querySendMsgList(Map<String, Object> params);

}
