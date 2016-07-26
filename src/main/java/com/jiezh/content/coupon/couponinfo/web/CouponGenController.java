package com.jiezh.content.coupon.couponinfo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.coupon.couponinfo.service.CouponGenService;
import com.jiezh.dao.coupon.generate.CouponInfoVO;
import com.jiezh.dao.coupon.partner.CouponTypeVO;

/**
 * 生成券controller类
 * 
 * @author houjiaqiang
 * @since 2016年7月11日 下午1:21:46
 */
@Controller
@RequestMapping("/coupon/gen/")
public class CouponGenController extends WebAction {

    @Autowired
    CouponGenService couponGenService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/coupon/generate/list");
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() {
        ModelAndView mv = new ModelAndView("/coupon/generate/list");
        CouponInfoVO couponInfoVO = (CouponInfoVO) getBean(CouponInfoVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", couponGenService.getcouponInfoByVO(couponInfoVO, currenPage));
        mv.addObject("condition", couponInfoVO);
        return mv;
    }

    @RequestMapping("queryCouponType")
    @ResponseBody
    public List<CouponTypeVO> queryCouponType() {
        String id = request.getParameter("id");
        return couponGenService.getCouponType(Long.valueOf(id));
    }

    @RequestMapping("save")
    public ModelAndView saveCouponInfo() throws Exception {
        CouponInfoVO couponInfoVO = (CouponInfoVO) getBean(CouponInfoVO.class);
        couponGenService.addCouponInfo(couponInfoVO, getUser());
        return queryList();
    }
}
