package com.jiezh.content.leads.export.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiezh.content.leads.export.service.GiftReportService;
import com.jiezh.dao.leads.client.ClientDao;

/**
 * 礼品发放service类
 * 
 * @author houjiaqiang
 * @since 2016年8月26日 下午5:34:40
 */
@Service
public class GiftReportServiceImp implements GiftReportService {
    @Autowired
    ClientDao clientDao;
    
    @Override
    public List<Map<String, Object>> getGift(Map<String, Object> param) {
        return clientDao.selectGiftReport(param);
    }
}
