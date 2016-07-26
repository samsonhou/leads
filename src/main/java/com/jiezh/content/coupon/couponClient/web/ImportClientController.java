package com.jiezh.content.coupon.couponClient.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.coupon.couponClient.service.ImportClientService;
import com.jiezh.dao.coupon.client.CouponClientVO;

/**
 * 客户导入controller类
 * 
 * @author houjiaqiang
 * @since 2016年7月7日 下午5:45:21
 */
@Controller
@RequestMapping("/coupon/client/")
public class ImportClientController extends WebAction {
    @Autowired
    ImportClientService importClientService;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("/coupon/clientinput/list");
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() {
        ModelAndView mv = new ModelAndView("/coupon/clientinput/list");
        CouponClientVO clientVO = (CouponClientVO) getBean(CouponClientVO.class);
        int currenPage = 1;
        if (request.getParameter("currenPage") != null && !"".equals(request.getParameter("currenPage"))) {
            currenPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("page", importClientService.getcouponClient(clientVO, currenPage));
        mv.addObject("condition", clientVO);
        return mv;
    }

    /**
     * 
     * 下载客户券模板模板
     * 
     * @param
     * @return Exception
     */
    @RequestMapping("downTemplate")
    public void downTemplate() throws Exception {
        String path = this.request.getSession().getServletContext().getRealPath("/") + "/res/pub/template/客户券模板.xls";
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("客户券模板.xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(buffer);
        outputStream.close();
        buffer = null;
    }

    /**
     * 通过模板文件导入客户信息
     * 
     * @param
     * @return Exception
     */
    @RequestMapping("importExcel")
    public ModelAndView importExcel(@RequestParam(value = "clientExcel") MultipartFile file) throws Exception {
        ModelAndView mv = new ModelAndView("/coupon/clientinput/list");
        AuthorUser user = this.getUser();
        String error = importClientService.processImportClient(file, user);
        if (StringUtils.isNotEmpty(error)) {
            mv.addObject("error", error);
            return mv;
        }
        return queryList();
    }

    @RequestMapping("sendMsg")
    @ResponseBody
    public Map<String, String> sendMsg() throws Exception {
        String ids = request.getParameter("ids");
        Map<String, String> map = new HashMap<>();
        map.put("msg", importClientService.processMsg(ids, getUser()));
        return map;
    }
}
