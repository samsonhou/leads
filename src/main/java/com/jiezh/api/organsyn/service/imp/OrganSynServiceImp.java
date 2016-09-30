package com.jiezh.api.organsyn.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiezh.api.organsyn.service.OrganSynService;
import com.jiezh.dao.api.orgsyn.OrganSynDao;
import com.jiezh.dao.api.orgsyn.OrganSynVO;

@Service
public class OrganSynServiceImp implements OrganSynService {

    @Autowired
    OrganSynDao organSynDao;

    public void insertOrganSynVO(OrganSynVO organSynVO) throws Exception {
        organSynDao.insertOrganSynVO(organSynVO);
    }

    public void updateOrganSynVO(OrganSynVO organSynVO) throws Exception {
        organSynDao.updateOrganSynVO(organSynVO);
    }

    public void deleteOrganSynVO(Long storeId) throws Exception {
        organSynDao.deleteOrganSynVO(storeId);
    }

    public boolean queryOrganCountByStoreId(Long storeId) throws Exception {
        Integer num = organSynDao.queryOrganCountByStoreId(storeId);
        return num.intValue() > 0;
    }

}
