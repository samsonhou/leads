package com.jiezh.dao.weixin.user;

import java.util.Map;

import com.jiezh.dao.weixin.user.UserVO;

public interface WeixinUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserVO record);

    int insertSelective(UserVO record);

    UserVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserVO record);

    int updateByPrimaryKey(UserVO record);
    
    String getUserByCode(Map<String,String> param);
}