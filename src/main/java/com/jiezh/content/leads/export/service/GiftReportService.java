package com.jiezh.content.leads.export.service;

import java.util.List;
import java.util.Map;


/**
 * 礼品发放service接口
 * 
 * @author houjiaqiang
 * @since 2016年8月26日 下午5:26:17
 */
public interface GiftReportService {
    public List<Map<String, Object>> getGift(Map<String, Object> param);

}
