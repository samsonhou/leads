package com.jiezh.content.coupon.partner.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.coupon.partner.service.PartnerService;
import com.jiezh.dao.base.city.BaseCityVO;
import com.jiezh.dao.coupon.partner.CouponPartnerVO;
import com.jiezh.dao.coupon.partner.CouponTypeVO;

/**
 * 合作商controller类
 * 
 * @author houjiaqiang
 * @since 2016年7月7日 下午3:15:11
 */
@Controller
@RequestMapping("/coupon/partner/")
public class PartnerController extends WebAction {

    @Autowired
    PartnerService partnerService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/coupon/partner/list");
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() {
        ModelAndView mv = new ModelAndView("/coupon/partner/list");
        String partnerName = request.getParameter("partnerName");
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", partnerService.getPartners(partnerName, currenPage));
        mv.addObject("partnerName", partnerName);

        return mv;
    }

    @RequestMapping("queryCity")
    @ResponseBody
    public List<BaseCityVO> queryCity() {
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) return partnerService.getCity(Long.valueOf(id));
        return null;
    }

    @RequestMapping("savePartner")
    public ModelAndView savePartner() throws Exception {
        AuthorUser user = getUser();
        CouponPartnerVO partnerVO = (CouponPartnerVO) getBean(CouponPartnerVO.class);
        partnerService.addPartner(partnerVO, user);
        return queryList();
    }

    @RequestMapping("editVO")
    @ResponseBody
    public CouponPartnerVO editVO() {
        String id = request.getParameter("id");
        return partnerService.getVoById(Long.valueOf(id));
    }

    @RequestMapping("saveCouponType")
    public ModelAndView saveCouponType() throws Exception {
        AuthorUser user = getUser();
        CouponTypeVO typeVO = (CouponTypeVO) getBean(CouponTypeVO.class);
        partnerService.addCouponType(typeVO, user);
        return queryList();
    }

    @RequestMapping("queryCoupons")
    @ResponseBody
    public List<CouponTypeVO> queryCoupons() {
        String id = request.getParameter("id");
        return partnerService.queryCoupons(Long.valueOf(id));
    }

    @RequestMapping("deleteCoupons")
    @ResponseBody
    public ModelAndView deleteCoupons() throws Exception {
        String id = request.getParameter("id");
        partnerService.removeCoupons(Long.valueOf(id), getUser());
        return queryList();
    }

}
