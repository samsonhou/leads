package com.jiezh.dao.leads.client;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.leads.erp.ErpDataVO;

public interface ClientDao {
    int deleteByPrimaryKey(Long id);

    int insert(ClientVO record);

    ClientVO selectByPrimaryKey(Long id);

    List<Map<String, Object>> selectClient(ClientVO clientVO);

    int updateByPrimaryKey(ClientVO record);

    List<Map<String, String>> querySub(String pid);

    // add by liangds 2015-12-21
    List<Map<String, Object>> getClientInfoByIds(List<String> list);

    int updateAssign(Map<String, Object> param);

    // add by cj
    int delData(Map<String, Object> param);

    // add by cj
    int renewData(Map<String, Object> param);

    List<Map<String, Object>> searchTrace(ClientVO clientVO);

    List<Map<String, Object>> queryOrgPerson(Map<String, Object> param);

    // add by liangds 2015-12-24
    List<Map<String, Object>> getVistList(ClientVO clientVO);

    List<Map<String, Object>> getAssignList(ClientVO clientVO);

    List<Map<String, Object>> getAssignByManager(ClientVO clientVO);

    List<Map<String, Object>> getClientList(ClientVO clientVO);

    List<Map<String, Object>> getSearchList(ClientVO clientVO);

    List<Map<String, Object>> getSearchList1(ClientVO clientVO);

    String searchPerson(long parseLong);

    String checkTel(String tel);

    List<Map<String, Object>> getReportHead();

    // add by cj 加入合计的表头
    List<Map<String, Object>> getReportHeadForExcel();

    List<Map<String, Object>> getCountAll(Map<String, Object> paras);

    List<Map<String, Object>> getClientListByTel(ClientVO clientVO);

    List<Map<String, Object>> getAssignListByTel(ClientVO clientVO);

    // add by cj 线索录入统计
    List<Map<String, Object>> getTotal(Map<String, Object> paras);

    List<Map<String, Object>> queryOrgPersonByRole(Map<String, Object> param);

    List<Map<String, Object>> queryOrgPersonManager(Map<String, Object> param);

    List<Map<String, Object>> queryOrgCustomerService(Map<String, Object> param);

    Map<String, Object> getHfCount(Map<String, Object> param);

    // add by cj 线索录入统计全部人员
    List<Map<String, Object>> getTotalAll(Map<String, Object> paras);

    // add by cj 获取分公司总量
    List<Map<String, Object>> getTotalPer(Map<String, Object> paras);

    // add by cj 获取分公司回复量
    List<Map<String, Object>> getReTotalPer(Map<String, Object> paras);

    // add by cj 获取分公司-人员 总量
    List<Map<String, Object>> getTotalPerOc(Map<String, Object> paras);

    // add by cj 获取分公司-人员回复量
    List<Map<String, Object>> getReTotalPerOc(Map<String, Object> paras);

    // add by cj 获取所有人员
    List<Map<String, Object>> getAllUser(Map<String, Object> paras);

    // add by cj 获取所有机构
    List<Map<String, Object>> getAllOrgan(Map<String, Object> paras);

    // 同一电话号码查询数量
    int selectTel(String tel);

    // add by liangds 2016-01-23
    void analyzeRepleat(Map<String, String> paras);

    List<Map<String, Object>> getAllReplyOrgan(String queryId);

    List<Map<String, Object>> getReplyList(Map<String, String> paras);

    // add by cj 查询线索总数
    String getClientCount();

    // ADD BY CJ 查询定制任务
    List<Map<String, Object>> getPlanList(Map<String, Object> paras);

    // ADD BY CJ 查询当月任务
    Map<String, Object> getPlan(Map<String, Object> paras);

    // ADD BY CJ 更新任务
    int insertPlan(Map<String, Object> paras);

    // ADD BY CJ 删除现有任务
    int deletePlanByID(Map<String, Object> paras);

    // ADD BY CJ 获取当前任务状态
    String getTaskState(Map<String, Object> paras);

    // ADD BY CJ 更新任务状态
    int updateTaskStateByID(long id);

    // add by cj 客户列表
    List<Map<String, Object>> getTimeoutList(Map<String, Object> paras);

    // add by cj 获取加分减分
    List<Map<String, Object>> getScore(Map<String, Object> paras);

    // add by cj 更新超时任务处理后的任务状态
    int updateTimeTaskByID(long id);

    // add by cj 更新催促任务处理后的任务状态
    int updateUrgeTaskByID(long id);

    // 获取当月积分
    String getTotalScore(Map<String, Object> paras);

    // add by cj 查询该用户的该条线索在本月是否被加过分
    Map<String, Object> getIsExist(Map<String, Object> paras);

    // add by cj 更新积分
    int updateScore(Map<String, Object> paras);

    // add by cj 更新任务量
    int updatePlan(Map<String, Object> paras);

    // add by cj 更新锁定积分
    int updateScoreLock(Map<String, Object> paras);

    // add by cj 新增记录
    int insertPlanScore(Map<String, Object> paras);

    // add by cj 更新记录
    int updatePlanScore(Map<String, Object> paras);

    // add by cj 是否为超时任务
    String getIsTimeOutTask(Map<String, Object> paras);

    // add by cj 获取催促数量和重要客户数量
    List<Map<String, Object>> getU_TCount(Map<String, Object> paras);

    // add by cj 当月任务是否存在
    String getPlanExistOfMonth(Map<String, Object> paras);

    // add by cj 初始化任务
    int initPlan(Map<String, Object> paras);

    // add by cj 获取销售列表
    List<Map<String, Object>> getUserList(Map<String, Object> paras);

    // add by cj 检查合同号是否存在
    String getIsExistsOfClient(String connum);

    // ADD BY CJ记录线索分配信息
    int insertAllocationItem(Map<String, Object> paras);

    // add by cj 报表导出
    List<Map<String, Object>> getExcelData(Map<String, Object> paras);

    // add by houjq 时效追踪
    List<ClientVO> selectClentByType(ClientVO clientVo);

    List<Map<String, Object>> getDDReport(Map<String, Object> paras);

    List<Map<String, Object>> selectDDRank(Map<String, Object> paras);

    List<Map<String, Object>> selectDDStatistics(Map<String, Object> paras);

    List<Map<String, Object>> selectDDStatisticsByTime(Map<String, Object> paras);

    List<Map<String, Object>> selectServiceTrace(ClientVO clientVO);

    List<Map<String, Object>> selectGiftReport(Map<String, Object> param);

    List<Map<String, Object>> queryShortTimeCustomerList(long rid);

    String queryUserRoleByUserId(Long userId);

    ErpDataVO selectErpInfoById(long clientId);

    // 属性不为空更新
    int updateByPrimaryKeySelective(ClientVO record);

    // 调用存过，处理工作交接
    public void dealHandover(Map<String, Object> param);
}
