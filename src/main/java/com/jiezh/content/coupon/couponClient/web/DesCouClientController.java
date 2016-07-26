package com.jiezh.content.coupon.couponClient.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.coupon.couponClient.service.DesCouClientService;

/**
 * 赠券核销Controller类
 * 
 * @author houjiaqiang
 * @since 2016年7月12日 下午4:48:02
 */
@Controller
@RequestMapping("/exchange/coupon/destory/")
public class DesCouClientController extends WebAction {

    @Autowired
    DesCouClientService desCouClientService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/coupon/destory/list");
        return mv;
    }

    @RequestMapping("doDestory")
    @ResponseBody
    public Map<String, String> doDestory() {
        String couponCode = request.getParameter("couponCode");
        Map<String, String> map = new HashMap<>();
        AuthorUser user = null;
        try {
            user = getUser();
        } catch (Exception e) {
            e.printStackTrace();
            user = new AuthorUser();
            user.setUserId(0);
        }

        map.put("msg", desCouClientService.processDestory(couponCode, user));
        return map;
    }

}
