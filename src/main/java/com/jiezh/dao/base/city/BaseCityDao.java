/*
 * BaseCityEntityDao.java
 * Copyright(C) 2016-2025 捷远公司
 * All rights reserved.
 * -----------------------------------------------
 * 2016-02-26 Created
 */
package com.jiezh.dao.base.city;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseCityDao {

    int insert(BaseCityVO record);

    int insertSelective(BaseCityVO record);

    int updateByPrimaryKeySelective(BaseCityVO record);

    int updateByPrimaryKey(BaseCityVO record);

    List<BaseCityVO> selecCityByParentId(@Param("id") Long id);

    BaseCityVO selectByCode(String code);
}
