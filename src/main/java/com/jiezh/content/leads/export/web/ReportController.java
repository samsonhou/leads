package com.jiezh.content.leads.export.web;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.content.base.pub.util.ExcelUtil;
import com.jiezh.content.base.pub.util.ExcelUtil.ExcelExportData;
import com.jiezh.content.base.pub.web.WebAction;
import com.jiezh.content.leads.service.ClientService;

/**
 * 报表相关
 * 2003 及以上
 * 
 * @author liangds
 *
 */

@Controller
@Scope("prototype")
@RequestMapping("/leads/report")
public class ReportController extends WebAction {
    Logger logger = Logger.getLogger(ReportController.class);
    @Autowired
    ClientService clientService;

    @RequestMapping("index")
    public ModelAndView index() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/clientlist");
        AuthorUser user = getUser();
        mv.addObject("organId", user.getOrganId());
        // modify by cj
        String stime = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 00:00:00";
        String etime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        mv.addObject("stnextdate", stime);
        mv.addObject("nextdate", etime);
        mv.addObject("ogId", user.getOrganId());
        return mv;
    }

    @RequestMapping("queryList")
    public ModelAndView queryList() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/clientlist");
        String stime = request.getParameter("stnextdate");
        String etime = request.getParameter("nextdate");
        String organId = request.getParameter("organId");
        String samllPid = request.getParameter("samllPid");

        AuthorUser user = getUser();

        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stime", stime);
        paras.put("etime", etime);
        paras.put("orgcode", user.getOrganCode());
        paras.put("organId", organId);
        paras.put("samllPid", samllPid);

        // add by cj 统计来源报表线索总量
        List<Map<String, Object>> countList = clientService.getCountAll(paras);
        Object count = countList.get(countList.size() - 1).get("count");
        countList.remove(countList.size() - 1);
        mv.addObject("count", count);

        mv.addObject("detail", countList);
        mv.addObject("head", clientService.getReportHead());

        mv.addObject("stnextdate", stime);
        mv.addObject("nextdate", etime);
        mv.addObject("organId", organId);
        mv.addObject("samllPid", samllPid);
        mv.addObject("ogId", user.getOrganId());

        return mv;
    }

    /**
     * 滴滴合作数据报表
     * 
     * @return
     * @author 王平
     * @throws Exception
     */
    @RequestMapping("ddindex")
    public ModelAndView ddindex() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/ddclientlist");

        String stime = request.getParameter("stnextdate");
        String etime = request.getParameter("nextdate");
        String depositStatus = request.getParameter("depositStatus");
        String fromType = request.getParameter("fromType");
        if (fromType == null) {
            mv.addObject("detail", new ArrayList<>());
            return mv;
        }

        if (stime == null || "".equals(stime)) stime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (etime == null || "".equals(etime)) etime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stime", stime);
        paras.put("etime", etime);
        paras.put("fromType", fromType);
        paras.put("depositStatus", depositStatus);
        paras.put("organId", getUser().getOrganId());

        List<Map<String, Object>> countList = clientService.getDDReport(paras);
        mv.addObject("detail", countList);
        // 统计合计
        Map<String, Object> heMap = new HashMap<String, Object>();

        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        for (Map<String, Object> map : countList) {
            num1 = num1 + Integer.parseInt("" + map.get("TOTNUM"));
            num2 = num2 + Integer.parseInt("" + map.get("YYNUM"));
            num3 = num3 + Integer.parseInt("" + map.get("DDNUM"));
            num4 = num4 + Integer.parseInt("" + map.get("JJNUM"));
            num5 = num5 + Integer.parseInt("" + map.get("TGNUM"));
            num6 = num6 + Integer.parseInt("" + map.get("TCNUM"));
        }

        heMap.put("ABBR_NAME", "合计");
        heMap.put("TOTNUM", num1);
        heMap.put("YYNUM", num2);
        heMap.put("DDNUM", num3);
        heMap.put("JJNUM", num4);
        heMap.put("TGNUM", num5);
        heMap.put("TCNUM", num6);
        mv.addObject("de", heMap);
        // 统计合计

        mv.addObject("stnextdate", stime);
        mv.addObject("nextdate", etime);
        mv.addObject("depositStatus", depositStatus);
        mv.addObject("fromType", fromType);
        return mv;
    }

    /**
     * 滴滴合作数据报表导出
     * 
     * @return
     * @author 王平
     * @throws Exception
     */
    @RequestMapping("excelList")
    @ResponseBody
    public void excelList() throws Exception {
        // String fileName = "滴滴合作数据报表";

        // 查询的计划列表
        //int currenPage = 1;

        String stime = request.getParameter("stnextdate");
        String etime = request.getParameter("nextdate");
        String depositStatus = request.getParameter("depositStatus");
        String fromType = request.getParameter("fromType");
        String fileName = "";
        switch (fromType) {
            case "391":
                fileName = "滴滴商城";
                break;
            case "550":
                fileName = "唯品会";
                break;
            case "790":
                fileName = "驾校一点通 ";
                break;
            case "730":
                fileName = "天翼积分商城";
                break;
            case "750":
                fileName = "兑吧";
                break;
            case "751":
                fileName = "天翼视讯";
                break;
            case "690":
                fileName = "脉脉";
                break;
            case "770":
                fileName = "天翼流量800";
                break;
            case "14":
                fileName = "汽车之家";
                break;
            case "810":
                fileName = "微信路况";
                break;
        }
        fileName += "数据报表";
        // String fileName = (fromType.equals("391")?"滴滴商城":"唯品会")+"数据报表";

        if (stime == null || "".equals(stime)) stime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (etime == null || "".equals(etime)) etime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stime", stime);
        paras.put("etime", etime);
        paras.put("organId", getUser().getOrganId());
        paras.put("depositStatus", depositStatus);
        paras.put("fromType", fromType);

        List<Map<String, Object>> countList = clientService.getDDReport(paras);
        // 查询内容不能为空
        if (!countList.isEmpty()) {

            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"分公司", "线索数量", "邀约数量", "到店数量", "风控进件数量", "风控批复数量", "客户提车数量"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"ABBR_NAME", "TOTNUM", "YYNUM", "DDNUM", "JJNUM", "TGNUM", "TCNUM"});

            // 统计合计
            Map<String, Object> heMap = new HashMap<String, Object>();

            int num1 = 0;
            int num2 = 0;
            int num3 = 0;
            int num4 = 0;
            int num5 = 0;
            int num6 = 0;
            for (Map<String, Object> map : countList) {
                num1 = num1 + Integer.parseInt("" + map.get("TOTNUM"));
                num2 = num2 + Integer.parseInt("" + map.get("YYNUM"));
                num3 = num3 + Integer.parseInt("" + map.get("DDNUM"));
                num4 = num4 + Integer.parseInt("" + map.get("JJNUM"));
                num5 = num5 + Integer.parseInt("" + map.get("TGNUM"));
                num6 = num6 + Integer.parseInt("" + map.get("TCNUM"));
            }

            heMap.put("ABBR_NAME", "合计");
            heMap.put("TOTNUM", num1);
            heMap.put("YYNUM", num2);
            heMap.put("DDNUM", num3);
            heMap.put("JJNUM", num4);
            heMap.put("TGNUM", num5);
            heMap.put("TCNUM", num6);
            countList.add(heMap);
            // 统计合计

            // 存入对应的主title名称和内容
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
            map.put(fileName, countList);

            // Excel导出数据类
            ExcelExportData setInfo = new ExcelExportData();
            setInfo.setDataMap(map); // 数据集
            setInfo.setColumnNames(columNames); // 对应列
            setInfo.setFieldNames(fieldNames); // 对应列属性
            setInfo.setTitles(new String[] {fileName + " (" + stime + "~" + etime + ")"}); // excel表下面工作表名

            // 设置response格式
            setResponeExcel(fileName);

            // 输出流
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(ExcelUtil.export2ByteArray1(setInfo));
            toClient.flush();
            toClient.close();

            System.out.println("导出报告是否成功：" + fileName + ".xls");
        } else {
            // 设置response格式
            setResponeExcel(fileName);
        }

    }

    @RequestMapping("excel/xlsx")
    public void excel() throws Exception {
        String stime = request.getParameter("stnextdate");
        String etime = request.getParameter("nextdate");
        String organId = request.getParameter("organId");
        String samllPid = request.getParameter("samllPid");

        AuthorUser user = getUser();

        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("stime", stime);
        paras.put("etime", etime);
        paras.put("orgcode", user.getOrganCode());
        paras.put("organId", organId);
        paras.put("samllPid", samllPid);

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
            + new String(("来源报表" + DateUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + ".xls").getBytes(), "iso-8859-1"));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(clientService.exportExcel(paras));
        outputStream.close();
    }

    @RequestMapping("report")
    public ModelAndView report() throws Exception {
        ModelAndView mv = new ModelAndView("leads/report/list");
        AuthorUser user = getUser();
        mv.addObject("ogId", user.getOrganId());
        return mv;
    }

    @RequestMapping("exportToClient")
    public ModelAndView reportToClient() {
        OutputStream outputStream = null;
        try {
            //String status = request.getParameter("status");
            String stime = request.getParameter("stnextdate");
            String stime1 = request.getParameter("stnextdate1");
            String organId = request.getParameter("organId");
            Map<String, Object> paras = new HashMap<String, Object>();
            // paras.put("date", status.equals("1")?stime:stime1);
            paras.put("stnextdate", stime);
            paras.put("stnextdate1", stime1);
            // paras.put("dateType", status.equals("1")?"YYYY-MM-DD":"YYYY-MM");
            paras.put("dateType", "YYYY-MM-DD");
            paras.put("organId", organId);
            // paras.put("status", status);
            outputStream = response.getOutputStream();
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("报表统计.xls").getBytes(), "iso-8859-1"));
            outputStream.write(clientService.exportToClient(paras));
        } catch (Exception e) {
            try {
                System.out.println(e.getStackTrace());
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("getExcelData")
    public ModelAndView getExcelData() throws Exception {
        ModelAndView mv = new ModelAndView("/leads/report/list");
        String status = request.getParameter("status");
        String stime = request.getParameter("stnextdate");
        String stime1 = request.getParameter("stnextdate1");
        String organId = request.getParameter("organId");
        AuthorUser user = getUser();
        Map<String, Object> paras = new HashMap<String, Object>();
        // paras.put("date", status.equals("1")?stime:stime1);
        paras.put("stnextdate", stime);
        paras.put("stnextdate1", stime1);
        // paras.put("dateType", status.equals("1")?"YYYY-MM-DD":"YYYY-MM");
        paras.put("dateType", "YYYY-MM-DD");
        paras.put("organId", organId);
        // paras.put("status", status);
        List<Map<String, Object>> list = clientService.getExcelData(paras);
        mv.addObject("stnextdate", stime);
        mv.addObject("stnextdate1", stime1);
        mv.addObject("list", list);
        mv.addObject("status", status);
        mv.addObject("organId", organId);
        mv.addObject("ogId", user.getOrganId());
        return mv;
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

    @RequestMapping("ddRank")
    public ModelAndView ddRank() throws Exception {
        String isQuery = request.getParameter("isQuery");
        ModelAndView mv = new ModelAndView("leads/report/ddRank");
        mv.addObject("orgId", getUser().getOrganId());
        if (StringUtils.isBlank(isQuery)) return mv;

        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        mv.addObject("list", clientService.getDDRank(paras));
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("organId", organId);
        mv.addObject("fromType", fromType);
        mv.addObject("orgId", getUser().getOrganId());

        return mv;
    }

    @RequestMapping("ddRankExport")
    public ModelAndView ddRankExport() throws Exception {
        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");
        // String fileName = (fromType.equals("391") ? "滴滴商城" : "唯品会") + "销售排行";

        String fileName = "";
        switch (fromType) {
            case "391":
                fileName = "滴滴商城";
                break;
            case "550":
                fileName = "唯品会";
                break;
            case "790":
                fileName = "驾校一点通";
                break;
            case "730":
                fileName = "天翼积分商城";
                break;
            case "750":
                fileName = "兑吧";
                break;
            case "751":
                fileName = "天翼视讯";
                break;
            case "690":
                fileName = "脉脉";
                break;
            case "770":
                fileName = "天翼流量800";
                break;
            case "14":
                fileName = "汽车之家";
                break;
            case "810":
                fileName = "微信路况";
                break;
        }
        fileName += "数据报表";

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        List<Map<String, Object>> list = clientService.getDDRank(paras);
        if (null != list && list.size() > 0) {
            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"客户经理", "所属机构", "风控通过数量", "成交数量"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"USERNAME", "ORGNAME", "FK", "CJ"});

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
            toClient.write(ExcelUtil.export2ByteArray1(setInfo));
            toClient.flush();
            toClient.close();

            logger.info("导出成功：" + fileName + ".xls");

        } else {
            setResponeExcel(fileName);
        }
        return null;
    }

    @RequestMapping("ddStatistics")
    public ModelAndView ddStatistics() throws Exception {
        String isQuery = request.getParameter("isQuery");
        ModelAndView mv = new ModelAndView("leads/report/ddStatistics");
        mv.addObject("orgId", getUser().getOrganId());
        if (StringUtils.isBlank(isQuery)) return mv;

        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        mv.addObject("list", clientService.getDDStatistics(paras));
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("organId", organId);
        mv.addObject("fromType", fromType);
        mv.addObject("orgId", getUser().getOrganId());

        return mv;
    }

    @RequestMapping("ddStatisticsExport")
    public ModelAndView ddStatisticsExport() throws Exception {
        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");
        // String fileName = (fromType.equals("391") ? "滴滴商城" : "唯品会") + "机构考核(统计率)";

        String fileName = "";
        switch (fromType) {
            case "391":
                fileName = "滴滴商城";
                break;
            case "550":
                fileName = "唯品会";
                break;
            case "790":
                fileName = "驾校一点通";
                break;
            case "730":
                fileName = "天翼积分商城";
                break;
            case "750":
                fileName = "兑吧";
                break;
            case "751":
                fileName = "天翼视讯";
                break;
            case "690":
                fileName = "脉脉";
                break;
            case "770":
                fileName = "天翼流量800";
                break;
            case "14":
                fileName = "汽车之家";
                break;
            case "810":
                fileName = "微信路况";
                break;
        }
        fileName += "数据报表";

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        List<Map<String, Object>> list = clientService.getDDStatistics(paras);
        if (null != list && list.size() > 0) {
            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"分类", "机构", "总线索", "邀约率", "到店率", "进件率", "通过率", "交车率", "投诉率", "备注"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"CATALOG", "NAME", "TOTNUM", "YYP", "DDP", "JJP", "TGP", "TCP", "TSP", ""});

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

    @RequestMapping("ddStatisticsByTime")
    public ModelAndView ddStatisticsByTime() throws Exception {
        String isQuery = request.getParameter("isQuery");
        ModelAndView mv = new ModelAndView("leads/report/ddStatisticsByTime");
        mv.addObject("orgId", getUser().getOrganId());
        if (StringUtils.isBlank(isQuery)) return mv;

        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        mv.addObject("list", clientService.getDDStatisticsByTime(paras));
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("organId", organId);
        mv.addObject("fromType", fromType);
        mv.addObject("orgId", getUser().getOrganId());

        return mv;
    }

    @RequestMapping("ddStatisticsByTimeExport")
    public ModelAndView ddStatisticsByTimeExport() throws Exception {
        String organId = request.getParameter("organId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String fromType = request.getParameter("fromType");
        // String fileName = (fromType.equals("391") ? "滴滴商城" : "唯品会") + "机构考核(节点平均时长)";
        String fileName = "";
        switch (fromType) {
            case "391":
                fileName = "滴滴商城";
                break;
            case "550":
                fileName = "唯品会";
                break;
            case "790":
                fileName = "驾校一点通";
                break;
            case "730":
                fileName = "天翼积分商城";
                break;
            case "750":
                fileName = "兑吧";
                break;
            case "751":
                fileName = "天翼视讯";
                break;
            case "690":
                fileName = "脉脉";
                break;
            case "770":
                fileName = "天翼流量800";
                break;
            case "14":
                fileName = "汽车之家";
                break;
            case "810":
                fileName = "微信路况";
                break;
        }
        fileName += "数据报表";

        Map<String, Object> paras = new HashMap<>();
        paras.put("organId", organId);
        paras.put("startDate", startDate);
        paras.put("endDate", endDate);
        paras.put("fromType", fromType);

        List<Map<String, Object>> list = clientService.getDDStatisticsByTime(paras);
        if (null != list && list.size() > 0) {
            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"分类", "机构", "总线索", "邀约平均时长(天)", "到店平均时长(天)", "进件平均时长(天)", "审核通过平均时长(天)", "交车平均时长(天)", "投诉平均时长(天)", "备注"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"CATALOG", "NAME", "TOTNUM", "YYAT", "DDAT", "JJAT", "TGAT", "TCAT", "TSAT", ""});

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

            logger.info("导出机构考核(节点平均时长)：" + fileName + ".xls");

        } else {
            setResponeExcel(fileName);
        }

        return null;
    }
}
