package com.jiezh.api.organsyn.service;

import com.jiezh.dao.api.orgsyn.OrganSynVO;

public interface OrganSynService {

    public void insertOrganSynVO(OrganSynVO organSynVO) throws Exception;

    public void updateOrganSynVO(OrganSynVO organSynVO) throws Exception;

    public void deleteOrganSynVO(Long storeId) throws Exception;

    public boolean queryOrganCountByStoreId(Long storeId) throws Exception;

}
