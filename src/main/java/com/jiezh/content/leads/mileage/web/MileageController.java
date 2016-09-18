package com.jiezh.content.leads.mileage.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.mileage.service.MileageService;
import com.jiezh.dao.leads.mileage.vo.CarRentelVO;
import com.jiezh.dao.leads.mileage.vo.CustomerVO;

/**
 * TODO 客户里程及满意度
 * 
 * @className MileageController
 * @author JXY
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/leads/mileage")
public class MileageController extends WebAction {

    public Log log = LogFactory.getLog(MileageController.class);

    @Autowired
    MileageService mileageService;

    /**
     * 首次列表页展示
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/mileage/list");
        Page<CustomerVO> page = new Page<CustomerVO>();
        mv.addObject("page", new PageInfo<CustomerVO>(page));
        mv.addObject("queryVO", new CustomerVO());
        return mv;
    }

    /**
     * 匹配查询数据列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("matchingQuery")
    public ModelAndView matchingQuery() throws Exception {
        CustomerVO queryVO = getQueryParameter();
        ModelAndView mv = new ModelAndView("leads/mileage/list");
        int currentPage = 1;
        if (StringUtils.isNotBlank(request.getParameter("currenPage"))) {
            currentPage = Integer.parseInt(request.getParameter("currenPage"));
        }
        mv.addObject("queryVO", queryVO);
        mv.addObject("page", mileageService.queryCustomerMileageList(currentPage, queryVO));
        return mv;
    }

    /**
     * 客户里程满意度详情
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("mileageDetail")
    public ModelAndView mileageDetail() throws Exception {
        ModelAndView mv = new ModelAndView("leads/mileage/info");
        Long customerid = Long.parseLong(request.getParameter("id"));
        Map<String, Object> resultMap = mileageService.queryCustomerMileageDetail(customerid);
        mv.addObject("customerVO", resultMap.get("customerVO"));
        mv.addObject("questionList", resultMap.get("questionList"));
        return mv;
    }

    /**
     * 客户里程表导出
     * 
     * @throws Exception
     */
    @RequestMapping("mileageExport")
    public void mileageExport() throws Exception {
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("客户里程与满意度数据表" + ".xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(mileageService.createExcel(getQueryParameter()));
        outputStream.close();
    }

    /**
     * 更改提醒状态
     * 
     * @throws Exception
     */
    @RequestMapping("updateRemind")
    @ResponseBody
    public Map<String, String> updateRemind() {
        Map<String, String> map = new HashMap<>();
        try {
            String[] mids = request.getParameter("mids").split(",");
            map.put("msg", mileageService.updateRemind(mids));
        } catch (Exception e) {
            map.put("msg", "更改客户提醒状态出错！");
        }
        return map;
    }

    /**
     * 模板下载
     * 
     * @throws Exception
     */
    @RequestMapping("downloadTemplate")
    public void downloadTemplate() throws Exception {
        String path = request.getSession().getServletContext().getRealPath("/") + "/res/pub/template/客户满意度及形式里程导入模板.xls";
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("客户满意度及形式里程导入模板" + ".xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(buffer);
        outputStream.close();
        buffer = null;
    }

    /**
     * 客户里程表导入
     * 
     * @throws Exception
     */
    @RequestMapping("mileageImport")
    public ModelAndView mileageImport() {
        ModelAndView mav = new ModelAndView("leads/mileage/list");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("uploadFile");
            if (!file.isEmpty()) {
                Map<String, Object> map = mileageService.importExcel(file.getInputStream(), file.getFileItem().getName());
                mav.addObject("msg", "导入成功！");
                mav.addObject("page", map.get("page"));
            }
        } catch (Exception e) {
            mav.addObject("msg", e.getMessage() == null ? "" : e.getMessage());
        }
        return mav;
    }

    private CustomerVO getQueryParameter() {
        CustomerVO queryVO = new CustomerVO();
        CarRentelVO carVO = new CarRentelVO();
        queryVO.setCustomername(request.getParameter("customername"));
        queryVO.setTel(request.getParameter("tel"));
        carVO.setCarvin(request.getParameter("carvin"));
        queryVO.setStore(request.getParameter("store"));
        queryVO.setNeedremind(request.getParameter("needremind"));
        queryVO.setCarRentelVO(carVO);
        return queryVO;
    }

}
