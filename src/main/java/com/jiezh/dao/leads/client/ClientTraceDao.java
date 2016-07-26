package com.jiezh.dao.leads.client;

import com.jiezh.dao.leads.client.ClientTraceVO;

public interface ClientTraceDao {
    int deleteByPrimaryKey(Long id);

    int insert(ClientTraceVO record);

    int insertSelective(ClientTraceVO record);

    ClientTraceVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientTraceVO record);

    int updateByPrimaryKey(ClientTraceVO record);
}