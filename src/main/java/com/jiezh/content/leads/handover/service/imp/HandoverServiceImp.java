package com.jiezh.content.leads.handover.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiezh.content.leads.handover.service.HandoverService;
import com.jiezh.dao.leads.client.ClientDao;

/**
 * 工作交接Service实现类
 * 
 * @author houjiaqiang
 * @since 2016年10月9日 下午4:28:28
 */
@Service
public class HandoverServiceImp implements HandoverService {

    @Autowired
    ClientDao clientDao;

    @Override
    public String processHandover(Map<String, Object> map) {
        clientDao.dealHandover(map);
        return map.get("msg").toString();
    }

}
