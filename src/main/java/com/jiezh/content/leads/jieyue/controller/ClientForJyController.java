package com.jiezh.content.leads.jieyue.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.jieyue.service.ClientForJyService;
import com.jiezh.dao.leads.clientimp.ClientImportVO;

/**
 * 捷越线索管理Controller类
 * 
 * @author houjiaqiang
 * @since 2016年9月12日 下午2:06:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/jy/")
public class ClientForJyController extends WebAction {
    @Autowired
    ClientForJyService clientForJyService;

    /**
     * 
     * 返回index页
     * 
     * @param
     * @return
     *         Exception
     */
    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("leads/client/clientlistforJy");
        return mv;
    }

    /**
     * 
     * 查询录入线索
     * 
     * @param
     * @return
     *         Exception
     */

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/client/clientlistforJy");
        ClientImportVO vo = (ClientImportVO) getBean(ClientImportVO.class);
        AuthorUser user = getUser();
        System.out.println(user.getUserRole().toString());
        if (!user.getUserRole().contains("1")) vo.setCreatedUserId(user.getUserId());
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", clientForJyService.getPageInfo(currenPage, vo));
        mv.addObject("clientVO", vo);
        return mv;
    }

    @RequestMapping("addClient")
    public ModelAndView addClient() throws Exception {
        ClientImportVO vo = (ClientImportVO) getBean(ClientImportVO.class);
        clientForJyService.saveClient(vo, getUser());
        return queryList();
    }

    @RequestMapping("checkTel")
    @ResponseBody
    public Map<String, Object> checkTel() {
        String tel = request.getParameter("param");
        return clientForJyService.queryByTel(tel);
    }
}
