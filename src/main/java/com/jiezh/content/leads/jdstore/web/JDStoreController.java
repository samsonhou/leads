package com.jiezh.content.leads.jdstore.web;

import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.jdstore.service.JDStoreService;
import com.jiezh.dao.leads.jdstore.JDStoreVO;

@Controller
@Scope("prototype")
@RequestMapping("/leads/jdstore/")
public class JDStoreController extends WebAction {

    public Log log = LogFactory.getLog(JDStoreController.class);

    @Autowired
    public JDStoreService storeService;

    /**
     * 首页
     * 
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        Page<JDStoreVO> page = new Page<JDStoreVO>();
        mav.addObject("page", new PageInfo<JDStoreVO>(page));
        mav.addObject("queryVO", new JDStoreVO());
        mav.setViewName("leads/jdstore/list");
        return mav;
    }

    /**
     * 匹配查询数据列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("matchingQuery")
    public ModelAndView matchingQuery() throws Exception {
        JDStoreVO queryVO = getQueryParameter();
        ModelAndView mv = new ModelAndView("leads/jdstore/list");
        int currentPage = 1;
        if (StringUtils.isNotBlank(request.getParameter("currenPage"))) {
            currentPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("queryVO", queryVO);
        mv.addObject("page", storeService.queryJDStoreTransactionList(currentPage, queryVO));
        return mv;
    }

    /**
     * 新增页面
     * 
     * @return
     */
    @RequestMapping("forAdd")
    public ModelAndView forAdd() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("oper", 1);
        mav.addObject("vo", new JDStoreVO());
        mav.setViewName("leads/jdstore/info");
        return mav;
    }

    /**
     * 修改页面
     * 
     * @return
     */
    @RequestMapping("forUpdate")
    public ModelAndView forUpdate() {
        ModelAndView mav = new ModelAndView();
        try {
            Long id = Long.valueOf(request.getParameter("id"));
            mav.addObject("oper", 2);
            mav.addObject("vo", storeService.queryJDStoreTransactionById(id));
            mav.setViewName("leads/jdstore/info");
        } catch (Exception e) {
            log.error("查询详细信息出错！", e);
            mav.addObject("msg", "获取交易明细失败！");
        }
        return mav;
    }

    /**
     * 保存
     * 
     * @param oper
     */
    @RequestMapping("save")
    @ResponseBody
    public ModelAndView save(String oper) {
        ModelAndView mav = new ModelAndView("leads/jdstore/info");
        try {
            AuthorUser loginUser = getUser();
            JDStoreVO storeVO = (JDStoreVO) getBean(JDStoreVO.class);
            storeVO = storeService.saveJDStoreTransaction(storeVO, oper, loginUser);
            mav.addObject("oper", 2);
            mav.addObject("vo", storeVO);
            mav.addObject("msg", "保存成功！");
        } catch (Exception e) {
            log.error("保存交易明细出错！", e);
            mav.addObject("msg", "系统错误，保存失败！");
        }
        return mav;
    }

    /**
     * 客户里程表导出
     * 
     * @throws Exception
     */
    @RequestMapping("export")
    public void export() {
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("京东店铺交易明细表" + ".xls").getBytes(), "iso-8859-1"));
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(storeService.createExcel(getQueryParameter()));
            outputStream.close();
        } catch (Exception e) {
            log.error("导出失败！", e);
        }
    }

    private JDStoreVO getQueryParameter() {
        JDStoreVO queryVO = new JDStoreVO();
        queryVO.setClientName(request.getParameter("clientName"));
        queryVO.setTel(request.getParameter("tel"));
        queryVO.setIdcard(request.getParameter("idcard"));
        queryVO.setOrderNo(request.getParameter("orderNo"));
        queryVO.setContractNo(request.getParameter("contractNo"));
        return queryVO;
    }

}
