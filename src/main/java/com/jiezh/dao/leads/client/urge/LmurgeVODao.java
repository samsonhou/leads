package com.jiezh.dao.leads.client.urge;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.leads.client.urge.LmurgeVO;

public interface LmurgeVODao {
    int deleteByPrimaryKey(Long id);

    int insert(LmurgeVO record);

    int insertSelective(LmurgeVO record);

    LmurgeVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LmurgeVO record);

    int updateByPrimaryKey(LmurgeVO record);
    
    List<LmurgeVO> selectByLmurge(LmurgeVO record);
    
    List<Map<String, Object>> selectByLmurgeList(Map<String,Object> paras);
}