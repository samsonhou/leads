package com.jiezh.content.leads.jdstore.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.content.leads.jdstore.service.JDStoreService;
import com.jiezh.content.leads.mileage.util.ExcelUtil;
import com.jiezh.dao.leads.jdstore.JDStoreDao;
import com.jiezh.dao.leads.jdstore.JDStoreVO;

@Service
public class JDStoreServiceImp implements JDStoreService {

    @Autowired
    public JDStoreDao jdstoreDao;

    /**
     * 保存交易明细
     */
    public JDStoreVO saveJDStoreTransaction(JDStoreVO storeVO, String oper, AuthorUser loginUser) throws Exception {
        if (StringUtils.equals("1", oper)) {
            // 新增
            storeVO.setCreateUser(loginUser.getUserId());
            jdstoreDao.insertJDStoreTransaction(storeVO);
        } else {
            // 修改
            storeVO.setUpdateUser(loginUser.getUserId());
            jdstoreDao.updateJDStoreTransaction(storeVO);
        }
        return storeVO;
    }

    /**
     * 分页查询明细列表
     */
    public PageInfo<JDStoreVO> queryJDStoreTransactionList(int currentPage, JDStoreVO storeVO) throws Exception {
        PageHelper.startPage(currentPage, Env.PAGE_SIZE);
        Page<JDStoreVO> page = (Page<JDStoreVO>) jdstoreDao.queryJDStoreTransactionList(storeVO);
        if (page == null) {
            return new PageInfo<JDStoreVO>();
        }
        return new PageInfo<JDStoreVO>(page);
    }

    /**
     * 查询明细列表
     */
    public List<JDStoreVO> queryJDStoreTransactionList(JDStoreVO storeVO) throws Exception {
        List<JDStoreVO> dataList = jdstoreDao.queryJDStoreTransactionList(storeVO);
        return dataList == null ? new ArrayList<JDStoreVO>() : dataList;
    }

    /**
     * 查询单条明细
     */
    public JDStoreVO queryJDStoreTransactionById(Long id) throws Exception {
        return jdstoreDao.queryJDStoreTransactionById(id);
    }

    /**
     * 数据导出
     */
    public byte[] createExcel(JDStoreVO queryVO) throws Exception {
        String sheetName = "京东店铺交易明细表";
        // 创建Excel标题列
        List<String> titles = buildExcelTitles();
        // 设置EXCEL指定列宽
        Map<Integer, Integer> map = setColumnWidth();
        // 将数据设置到EXCEL表格中
        List<Map<Integer, Object>> rowList = new ArrayList<Map<Integer, Object>>();// Excel表格数据
        queryVO.setLastMonthDate(DateUtil.getDateOfLastMonth(new Date()));
        List<JDStoreVO> dataList = queryJDStoreTransactionList(queryVO); // 原始数据
        for (int i = 0; i < dataList.size(); i++) {
            Map<Integer, Object> cellMap = new HashMap<Integer, Object>();
            JDStoreVO vo = dataList.get(i);
            cellMap.put(0, i + 1);
            cellMap.put(1, vo.getClientName());
            cellMap.put(2, vo.getIdcard());
            cellMap.put(3, vo.getTel());
            cellMap.put(4, vo.getProductType());

            cellMap.put(5, vo.getOrderNo());
            cellMap.put(6, vo.getOrderStatus());
            cellMap.put(7, vo.getOrderAmount());
            cellMap.put(8, vo.getPayTime());

            cellMap.put(9, vo.getStoreName());
            cellMap.put(10, vo.getSignedUser());
            cellMap.put(11, vo.getContractNo());
            cellMap.put(12, vo.getCarType());
            cellMap.put(13, vo.getCarVin());

            rowList.add(cellMap);
        }
        return ExcelUtil.export2Excel(sheetName, titles, rowList, map, "京东店铺交易明细表");
    }

    // 数据导出-设置标题列
    private List<String> buildExcelTitles() {
        List<String> titles = new ArrayList<String>(30);
        titles.add("序号");
        titles.add("客户姓名");
        titles.add("身份证号");
        titles.add("手机号");
        titles.add("活动产品类型");
        titles.add("京东订单号");
        titles.add("订单状态");
        titles.add("订单金额");
        titles.add("支付完成时间");
        titles.add("门店名称");
        titles.add("签约主体（合同盖章名称）");
        titles.add("合同号");
        titles.add("车型");
        titles.add("车架号");
        return titles;
    }

    // 数据导出-设置导出列宽
    private Map<Integer, Integer> setColumnWidth() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 8);
        map.put(1, 16);
        map.put(2, 25);
        map.put(3, 20);
        map.put(4, 25);
        map.put(5, 20);
        map.put(6, 16);
        map.put(7, 16);
        map.put(8, 20);
        map.put(9, 20);
        map.put(10, 33);
        map.put(11, 20);
        map.put(12, 20);
        map.put(13, 25);
        return map;
    }

}
