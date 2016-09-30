package com.jiezh.content.base.fromtype.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.fromtype.service.FromTypeService;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.dao.base.fromtype.BaseSourceVO;

/**
 * 来源controller类
 * 
 * @author houjiaqiang
 * @since 2016年9月19日 上午11:10:13
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/base/fromtype")
public class FromTypeController extends WebAction {
    @Autowired
    FromTypeService fromTypeService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/base/fromtype/list");
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() {
        ModelAndView mv = new ModelAndView("/base/fromtype/list");
        BaseSourceVO vo = (BaseSourceVO) getBean(BaseSourceVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", fromTypeService.getPageInfo(vo, currenPage));
        mv.addObject("vo", vo);
        return mv;
    }

    @RequestMapping("queryFromtype")
    @ResponseBody
    public Map<String, Object> queryFromtype() {
        String pid = request.getParameter("pid");
        String level = request.getParameter("level");
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        map.put("level", Integer.valueOf(level)+1);
        return fromTypeService.getFromtype(map);
    }

    @RequestMapping("save")
    public ModelAndView save() throws Exception {
        BaseSourceVO vo = (BaseSourceVO) getBean(BaseSourceVO.class);
        fromTypeService.addFromtype(vo, getUser());
        return queryList();

    }
}
