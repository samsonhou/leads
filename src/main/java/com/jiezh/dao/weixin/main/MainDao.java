package com.jiezh.dao.weixin.main;

import com.jiezh.dao.weixin.main.MainVO;
import java.math.BigDecimal;

public interface MainDao {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(MainVO record);

    int insertSelective(MainVO record);

    MainVO selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(MainVO record);

    int updateByPrimaryKey(MainVO record);
}