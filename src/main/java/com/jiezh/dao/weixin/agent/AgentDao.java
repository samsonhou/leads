package com.jiezh.dao.weixin.agent;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.weixin.agent.AgentVO;

public interface AgentDao {
    int deleteByPrimaryKey(Long id);

    int insert(AgentVO record);

    int insertSelective(AgentVO record);

    AgentVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AgentVO record);

    int updateByPrimaryKey(AgentVO record);
    
    List<Map<String,Object>> getWxAgents(int id);
}