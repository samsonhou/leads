package com.jiezh.content.leads.export.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.util.ExcelUtil;
import com.jiezh.content.base.pub.util.ExcelUtil.ExcelExportData;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.export.service.GiftReportService;

/**
 * 礼品发放controller类
 * 
 * @author houjiaqiang
 * @since 2016年8月26日 下午5:10:44
 */
@Controller
@RequestMapping("/leads/report/gift")
public class GiftReportController extends WebAction {
    Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    GiftReportService giftReportService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/giftReport");
        mv.addObject("orgId", getUser().getOrganId());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/giftReport");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String organId = request.getParameter("organId");
        Map<String, Object> param = new HashMap<>();
        param.put("organId", organId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        mv.addObject("orgId", getUser().getOrganId());
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("organId", organId);
        mv.addObject("list", giftReportService.getGift(param));
        return mv;
    }

    @RequestMapping("giftExport")
    public ModelAndView giftExport() throws Exception {
        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String fileName = "礼品发放统计报表";

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);

        List<Map<String, Object>> list = giftReportService.getGift(paras);
        if (null != list && list.size() > 0) {
            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"门店", "到店礼", "订车礼", "交车礼"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"ORGNAME", "SHOPGIFT", "ORDERGIFT", "CARGIFT"});

            // 存入对应的主title名称和内容
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();

            map.put(fileName, list);
            // Excel导出数据类
            ExcelExportData setInfo = new ExcelExportData();
            setInfo.setDataMap(map); // 数据集
            setInfo.setColumnNames(columNames); // 对应列
            setInfo.setFieldNames(fieldNames); // 对应列属性
            setInfo.setTitles(new String[] {fileName + " (" + startDate + "~" + endDate + ")"}); // excel表下面工作表名

            // 设置response格式
            setResponeExcel(fileName);

            // 输出流
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(ExcelUtil.export2ByteArray2(setInfo));
            toClient.flush();
            toClient.close();

            logger.info("导出成功：" + fileName + ".xls");

        } else {
            setResponeExcel(fileName);
        }

        return null;
    }

    /**
     * 设置下载的Excel表格式
     * 
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public void setResponeExcel(String fileName) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
    }

}
