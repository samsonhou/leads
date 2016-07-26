package com.jiezh.dao.coupon.client;

import java.util.List;
import java.util.Map;

public interface CouponClientVODao {
    int deleteByPrimaryKey(Long id);

    int insert(CouponClientVO record);

    int insertSelective(CouponClientVO record);

    CouponClientVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponClientVO record);

    int updateByPrimaryKey(CouponClientVO record);

    int insertByImport(CouponClientVO record);

    List<CouponClientVO> selectByVO(CouponClientVO record);

    CouponClientVO selectSendInfo(Map<String, Object> params);

    int updateByCouponCode(Map<String, Object> params);
}
