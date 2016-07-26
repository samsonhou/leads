package com.jiezh.dao.leads.client.update;

public interface ClientUpdateDao {
    int deleteByPrimaryKey(Long id);

    int insert(ClientUpdateVO record);

    int insertSelective(ClientUpdateVO record);

    ClientUpdateVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientUpdateVO record);

    int updateByPrimaryKey(ClientUpdateVO record);
}