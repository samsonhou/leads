package com.jiezh.dao.base.sysvar;

import org.apache.ibatis.annotations.Param;

public interface SysvarVODao {
    int deleteByPrimaryKey(Long id);

    int insert(SysvarVO record);

    int insertSelective(SysvarVO record);

    SysvarVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysvarVO record);

    int updateByPrimaryKey(SysvarVO record);

    SysvarVO selectBySysvarCode(@Param(value = "sysvarCode") String sysvarCode);
}
