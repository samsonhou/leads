package com.jiezh.content.coupon.couponinfo.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.coupon.generate.CouponInfoVO;
import com.jiezh.dao.coupon.partner.CouponTypeVO;

/**
 * 生成券service接口类
 * 
 * @author houjiaqiang
 * @since 2016年7月11日 下午1:33:36
 */
public interface CouponGenService {
    public List<CouponTypeVO> getCouponType(long id);

    public void addCouponInfo(CouponInfoVO couponInfoVO, AuthorUser user);

    public PageInfo<CouponInfoVO> getcouponInfoByVO(CouponInfoVO couponInfoVO, int curPage);
}
