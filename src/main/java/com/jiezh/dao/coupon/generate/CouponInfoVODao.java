package com.jiezh.dao.coupon.generate;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.coupon.client.CouponClientVO;

public interface CouponInfoVODao {
    int deleteByPrimaryKey(Long id);

    int insert(CouponInfoVO record);

    int insertSelective(CouponInfoVO record);

    CouponInfoVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CouponInfoVO record);

    int updateByPrimaryKey(CouponInfoVO record);

    List<CouponInfoVO> selectCouponInfoByVO(CouponInfoVO record);

    int updateByImport(CouponClientVO record);

    int updateByCouponCode(Map<String, Object> params);

    List<Map<String, Object>> selectByCheck(CouponClientVO clientVO);
}
