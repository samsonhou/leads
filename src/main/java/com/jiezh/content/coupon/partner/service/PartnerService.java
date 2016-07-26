package com.jiezh.content.coupon.partner.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.base.city.BaseCityVO;
import com.jiezh.dao.coupon.partner.CouponPartnerVO;
import com.jiezh.dao.coupon.partner.CouponTypeVO;

/**
 * 合作商service接口
 * 
 * @author houjiaqiang
 * @since 2016年7月8日 上午11:46:20
 */
public interface PartnerService {
    public List<BaseCityVO> getCity(Long id);

    public void addPartner(CouponPartnerVO partnerVO, AuthorUser user);

    public void addCouponType(CouponTypeVO typeVO, AuthorUser user);

    public PageInfo<CouponPartnerVO> getPartners(String partnerName, int curPage);

    public CouponPartnerVO getVoById(long id);

    public List<CouponTypeVO> queryCoupons(long id);

    public void removeCoupons(long id, AuthorUser user);

}
