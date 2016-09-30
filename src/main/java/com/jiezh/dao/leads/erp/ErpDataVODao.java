package com.jiezh.dao.leads.erp;


public interface ErpDataVODao {
    int deleteByPrimaryKey(Long id);

    int insert(ErpDataVO record);

    int insertSelective(ErpDataVO record);

    ErpDataVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ErpDataVO record);

    int updateByPrimaryKey(ErpDataVO record);
   
}