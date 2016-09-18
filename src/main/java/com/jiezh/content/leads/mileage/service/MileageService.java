package com.jiezh.content.leads.mileage.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.dao.leads.mileage.vo.CustomerVO;

/**
 * TODO 客户里程与满意度SERVICE
 * 
 * @className MileageService
 * @author JXY
 * @version V1.0
 */
public interface MileageService {

    /**
     * 里程及满意度数据导出
     * 
     * @return
     * @throws Exception
     */
    public byte[] createExcel(CustomerVO queryVO) throws Exception;

    /**
     * 客户里程满意度导入
     * 
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public Map<String, Object> importExcel(InputStream in, String fileName) throws Exception;

    public void saveImportDatas(List<List<Object>> rowList) throws Exception;

    /**
     * 更改提醒状态
     * 
     * @param mids
     * @return
     * @throws Exception
     */
    public String updateRemind(String[] mids) throws Exception;

    /**
     * 分页查询列表
     * 
     * @param currentPage
     * @param mileageVO
     * @return
     * @throws Exception
     */
    public PageInfo<CustomerVO> queryCustomerMileageList(int currentPage, CustomerVO mileageVO) throws Exception;

    /**
     * 根据客户ID查询客户里程满意度详情
     * 
     * @param customerid
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryCustomerMileageDetail(Long customerid) throws Exception;

}
