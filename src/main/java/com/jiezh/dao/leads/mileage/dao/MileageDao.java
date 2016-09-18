package com.jiezh.dao.leads.mileage.dao;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.leads.mileage.vo.CarRentelVO;
import com.jiezh.dao.leads.mileage.vo.CustomerVO;
import com.jiezh.dao.leads.mileage.vo.MileageVO;
import com.jiezh.dao.leads.mileage.vo.QuestionVO;
import com.jiezh.dao.leads.mileage.vo.SatisfactionVO;

public interface MileageDao {

    // 保存客户基本信息
    public Long insertCustomerInfo(CustomerVO customerVO) throws Exception;

    // 更新客户基本信息
    public Long updateCustomerInfo(CustomerVO customerVO) throws Exception;

    // 保存租用车辆信息
    public Long insertCarRentelInfo(CarRentelVO carRentelVO) throws Exception;

    // 更新租用车辆信息
    public Long updateCarRentelInfo(CarRentelVO carRentelVO) throws Exception;

    // 保存行驶里程信息
    public int insertMileageInfo(MileageVO mileageVO) throws Exception;

    // 更新提醒状态
    public int updateRemind(List<MileageVO> mileageList) throws Exception;

    // 批量保存满意度调查信息
    public int batchInsertSatisfactionInfo(List<SatisfactionVO> satisfactionList) throws Exception;

    // 更新满意度调查信息
    public int updateSatisfactionInfo(List<SatisfactionVO> satisfactionList) throws Exception;

    // 分页查询客户里程信息
    public List<CustomerVO> queryCustomerMileageList(CustomerVO customerVO) throws Exception;

    // 查询满足条件的所有客户里程信息
    public List<CustomerVO> queryMatchingCustomerMileageList(CustomerVO customerVO) throws Exception;

    // 根据客户电话查询客户及租用车辆行驶里程信息
    public CustomerVO queryCarMileageByCustomertel(Map<String, Object> paraMap) throws Exception;

    // 根据客户ID查询客户里程详情
    public CustomerVO queryCustomerMileageDetail(Long customerid) throws Exception;

    // 根据客户ID查询满意度详情
    public List<QuestionVO> querySatisfactionDetail(Long customerid) throws Exception;

}
