package com.jiezh.content.coupon.couponinfo.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.coupon.couponinfo.service.CouponGenService;
import com.jiezh.dao.coupon.generate.CouponInfoVO;
import com.jiezh.dao.coupon.generate.CouponInfoVODao;
import com.jiezh.dao.coupon.partner.CouponTypeVO;
import com.jiezh.dao.coupon.partner.CouponTypeVODao;

/**
 * 生成券service实现类
 * 
 * @author houjiaqiang
 * @since 2016年7月11日 下午1:34:04
 */
@Service
public class CouponGenServiceImp implements CouponGenService {

    @Autowired
    CouponTypeVODao couponTypeVODao;
    @Autowired
    CouponInfoVODao couponInfoVODao;

    @Override
    public List<CouponTypeVO> getCouponType(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return couponTypeVODao.selectByMap(params);
    }

    @Override
    public void addCouponInfo(CouponInfoVO couponInfoVO, AuthorUser user) {
        couponInfoVO.setCreatedUserId(user.getUserId());
        couponInfoVO.setStatus("1");
        for (int i = 0; i < couponInfoVO.getCount(); i++) {
            couponInfoVODao.insert(couponInfoVO);
        }
    }

    @Override
    public PageInfo<CouponInfoVO> getcouponInfoByVO(CouponInfoVO couponInfoVO, int curPage) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<CouponInfoVO> page = (Page<CouponInfoVO>) couponInfoVODao.selectCouponInfoByVO(couponInfoVO);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<>(page);
    }

}
