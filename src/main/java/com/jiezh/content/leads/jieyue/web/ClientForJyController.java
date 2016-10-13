package com.jiezh.content.leads.jieyue.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.ExcelUtil;
import com.jiezh.content.base.pub.util.ExcelUtil.ExcelExportData;
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
    private static Logger logger = Logger.getLogger(ClientForJyController.class);
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

    @RequestMapping("exp")
    public void exp() throws Exception {
        AuthorUser user = getUser();
        ClientImportVO vo = (ClientImportVO) getBean(ClientImportVO.class);
        if (!user.getUserRole().contains("1")) vo.setCreatedUserId(user.getUserId());
        List<Map<String,Object>> list = clientForJyService.getVoList(vo);
        String fileName = "捷越导出数据";
        if (null != list && list.size() > 0) {
            // 开始组装数据
            // 每列title名称
            List<String[]> columNames = new ArrayList<String[]>();
            columNames.add(new String[] {"客户姓名", "身份证号", "电话", "客户经理","城市","门店","录入时间","状态"});

            // 每列对应字段
            List<String[]> fieldNames = new ArrayList<String[]>();
            fieldNames.add(new String[] {"CLIENTNAME", "IDNO", "TEL", "CUSTOMERMANAGER","CITY","ORGAN","CREATEDTIME","STATUS"});

            // 存入对应的主title名称和内容
            LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();

            map.put(fileName, list);
            // Excel导出数据类
            ExcelExportData setInfo = new ExcelExportData();
            setInfo.setDataMap(map); // 数据集
            setInfo.setColumnNames(columNames); // 对应列
            setInfo.setFieldNames(fieldNames); // 对应列属性
            setInfo.setTitles(new String[] {"捷越导出数据"}); // excel表下面工作表名

            // 设置response格式
            setResponeExcel(fileName);

            // 输出流
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(ExcelUtil.export2Stream(setInfo).toByteArray());
            toClient.flush();
            toClient.close();

            logger.info("导出成功：" + fileName + ".xls");

        } else {
            setResponeExcel(fileName);
        }
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
