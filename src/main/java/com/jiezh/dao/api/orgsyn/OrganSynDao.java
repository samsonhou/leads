package com.jiezh.dao.api.orgsyn;

public interface OrganSynDao {

    public void insertOrganSynVO(OrganSynVO organSynVO) throws Exception;

    public void updateOrganSynVO(OrganSynVO organSynVO) throws Exception;

    public void deleteOrganSynVO(Long storeId) throws Exception;

    public Integer queryOrganCountByStoreId(Long storeId) throws Exception;

}
