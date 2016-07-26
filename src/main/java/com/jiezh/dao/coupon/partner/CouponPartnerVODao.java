package com.jiezh.dao.coupon.partner;

import java.util.List;

public interface CouponPartnerVODao {
    int deleteByPrimaryKey(Long id);

    int insert(CouponPartnerVO record);

    int insertSelective(CouponPartnerVO record);

    CouponPartnerVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponPartnerVO record);

    int updateByPrimaryKey(CouponPartnerVO record);

    List<CouponPartnerVO> selectByVO(CouponPartnerVO record);

    List<CouponTypeVO> selectCoupons(Long id);
}
