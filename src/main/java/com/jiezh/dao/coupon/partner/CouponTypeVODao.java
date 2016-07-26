package com.jiezh.dao.coupon.partner;

import java.util.List;
import java.util.Map;

public interface CouponTypeVODao {

    int deleteByPrimaryKey(Long id);

    int insert(CouponTypeVO record);

    int insertSelective(CouponTypeVO record);

    CouponTypeVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponTypeVO record);

    int updateByPrimaryKey(CouponTypeVO record);

    List<CouponTypeVO> selectByMap(Map<String, Object> params);
}
