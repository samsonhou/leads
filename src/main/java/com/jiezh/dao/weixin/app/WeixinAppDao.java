package com.jiezh.dao.weixin.app;

import com.jiezh.dao.weixin.app.WeixinAppVO;

public interface WeixinAppDao {
    int deleteByPrimaryKey(Long id);

    int insert(WeixinAppVO record);

    int insertSelective(WeixinAppVO record);

    WeixinAppVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeixinAppVO record);

    int updateByPrimaryKey(WeixinAppVO record);
}