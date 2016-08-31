package com.jiezh.dao.leads.activity;

public interface ActivityConfigVODao {
    int deleteByPrimaryKey(Long id);

    int insert(ActivityConfigVO record);

    int insertSelective(ActivityConfigVO record);

    ActivityConfigVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityConfigVO record);

    int updateByPrimaryKey(ActivityConfigVO record);
}