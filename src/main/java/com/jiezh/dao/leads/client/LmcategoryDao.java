package com.jiezh.dao.leads.client;

import java.util.List;

import com.jiezh.dao.leads.client.LmcategoryVO;

public interface LmcategoryDao {
    int deleteByPrimaryKey(long id);
    int insert(LmcategoryVO record);
    int insertSelective(LmcategoryVO record);
    int updateByPrimaryKey(LmcategoryVO record);
 
    LmcategoryVO selectByPrimaryKey(long id);
    List<LmcategoryVO> selectByLmcategory(LmcategoryVO record);
}