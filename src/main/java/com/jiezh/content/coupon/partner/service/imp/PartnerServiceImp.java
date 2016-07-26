package com.jiezh.content.coupon.partner.service.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.coupon.partner.service.PartnerService;
import com.jiezh.dao.base.city.BaseCityDao;
import com.jiezh.dao.base.city.BaseCityVO;
import com.jiezh.dao.coupon.partner.CouponPartnerVO;
import com.jiezh.dao.coupon.partner.CouponPartnerVODao;
import com.jiezh.dao.coupon.partner.CouponTypeVO;
import com.jiezh.dao.coupon.partner.CouponTypeVODao;

/**
 * 合作商service类
 * 
 * @author houjiaqiang
 * @since 2016年7月8日 上午11:48:15
 */
@Service
public class PartnerServiceImp implements PartnerService {

    @Autowired
    BaseCityDao baseCityDao;
    @Autowired
    CouponPartnerVODao couponPartnerVODao;
    @Autowired
    CouponTypeVODao couponTypeVODao;

    @Override
    public List<BaseCityVO> getCity(Long id) {
        return baseCityDao.selecCityByParentId(id);
    }

    @Override
    public void addPartner(CouponPartnerVO partnerVO, AuthorUser user) {
        if (null != partnerVO.getId() && StringUtils.isNotBlank(partnerVO.getId().toString())) {
            partnerVO.setUpdatedUserId(user.getUserId());
            couponPartnerVODao.updateByPrimaryKey(partnerVO);
        } else {
            partnerVO.setCreatedUserId(user.getUserId());
            couponPartnerVODao.insert(partnerVO);
        }

    }

    public PageInfo<CouponPartnerVO> getPartners(String partnerName, int curPage) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        CouponPartnerVO partnerVO = new CouponPartnerVO();
        partnerVO.setPartnerName(partnerName);
        Page<CouponPartnerVO> page = (Page<CouponPartnerVO>) couponPartnerVODao.selectByVO(partnerVO);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<>(page);
    }

    @Override
    public CouponPartnerVO getVoById(long id) {
        return couponPartnerVODao.selectByPrimaryKey(id);
    }

    @Override
    public List<CouponTypeVO> queryCoupons(long id) {
        return couponPartnerVODao.selectCoupons(id);
    }

    @Override
    public void addCouponType(CouponTypeVO typeVO, AuthorUser user) {
        typeVO.setCreatedUserId(user.getUserId());
        couponTypeVODao.insert(typeVO);
    }

    @Override
    public void removeCoupons(long id, AuthorUser user) {
        CouponTypeVO typeVO = new CouponTypeVO();
        typeVO.setId(id);
        typeVO.setStatus("0");
        typeVO.setUpdatedUserId(user.getUserId());
        couponTypeVODao.updateByPrimaryKeySelective(typeVO);
    }

}
