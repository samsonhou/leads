package com.jiezh.content.leads.assign.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.token.Token;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.assign.service.WaitingAssignService;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.leads.clientimp.ClientImportVO;

/**
 * 待分配
 * 
 * @author houjq
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/assign/wait/")
public class WaitingAssignController extends WebAction {
    @Autowired
    ClientService clientService;
    @Autowired
    WaitingAssignService waitingAssignService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/waitinglist");
        return mv;
    }


    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/waitinglist");
        ClientImportVO vo = (ClientImportVO) getBean(ClientImportVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }

        mv.addObject("page", waitingAssignService.getPageList(currenPage, vo));
        mv.addObject("vo", vo);
        return mv;
    }

    @Token(generate = true)
    @RequestMapping("toAssgin")
    public ModelAndView toAssginPage() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/waitinginfo");
        String assginItems = request.getParameter("assginItems");
        Map<String, Object> param = new HashMap<>();
        if (assginItems != null && !"".equals(assginItems)) {
            param.put("ids", Arrays.asList(assginItems.split(",")));
        }
        mv.addObject("list", waitingAssignService.getVoList(param));
        return mv;
    }

    @Token(validate = true)
    @RequestMapping("assgin")
    public ModelAndView assgin() throws Exception {
        ModelAndView mv = new ModelAndView("leads/assign/waitinginfo");
        String ids[] = request.getParameterValues("clientId");
        String newUserId = request.getParameter("newUserId");
        String newUserOrganId = request.getParameter("newUserOrganId");
        String newUserCode = request.getParameter("newUserCode");

        Map<String, Object> param = new HashMap<>();
        param.put("ids", ids);
        param.put("rid", newUserId);
        param.put("organId", newUserOrganId);

        int count = waitingAssignService.processAssign(getUser(), param);

        mv.addObject("message", "已将" + count + "条线索分配给" + newUserCode);

        return mv;
    }

    /**
     * 线索导入
     * 
     * @throws Exception
     */
    @RequestMapping("clueImport")
    public ModelAndView mileageImport() {
        ModelAndView mav = new ModelAndView("leads/assign/waitinglist");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("uploadFile");
            if (!file.isEmpty()) {
                Map<String, Object> map = waitingAssignService.importExcel(file.getInputStream(), file.getFileItem().getName());
                mav.addObject("msg", map.get("msg"));
                mav.addObject("page", map.get("page"));
            }
        } catch (Exception e) {
            mav.addObject("msg", "获取导入文件流出错！");
        }
        return mav;
    }

    /**
     * 模板下载
     * 
     * @throws Exception
     */
    @RequestMapping("downTemplate")
    public void downloadTemplate() throws Exception {
        String path = request.getSession().getServletContext().getRealPath("/") + "/res/pub/template/线索导入模板.xls";
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("线索导入模板" + ".xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(buffer);
        outputStream.close();
        buffer = null;
    }

}
