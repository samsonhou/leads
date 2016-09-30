package com.jiezh.content.leads.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.leads.vist.web.VistBean;
import com.jiezh.dao.base.codetype.CodeItemVO;
import com.jiezh.dao.leads.activity.ActivityConfigVO;
import com.jiezh.dao.leads.client.ClientTraceVO;
import com.jiezh.dao.leads.client.ClientVO;

public interface ClientService {
    public int addClient(ClientVO recode) throws Exception;

    public int updateClient(ClientVO recode, AuthorUser user) throws Exception;

    public PageInfo<Map<String, Object>> search(int currenPage, ClientVO vo);

    public String querySub(String bigPid);

    // 查询需分配任务
    public List<Map<String, Object>> getClientAssginTask(List<String> list);

    public int updateTaskUser(List<String> ids, String newUserId, String newUserOrganId);

    // add by cj 单个/批量删除线索
    public int delData(List<String> ids);

    // add by cj 线索恢复
    public int renewData(List<String> ids);

    // 查询追踪
    public List<Map<String, Object>> searchTrace(ClientVO clientVO);

    // 查询追踪的客户信息
    public Map<String, Object> searchClient(ClientVO clientVO);

    // 保存追踪和客户
    public void saveTrace(ClientVO clientVO, ClientTraceVO clientTraceVO);

    public ClientVO searchVo(String clID);

    public String queryOrgPerson(String from, String organId, Long userId, String organCode);

    // 查找处理的人
    public String searchPerson(String clID);

    // 异步校验电话
    public String checkTel(String tel);

    // add by liangds 2015-12-24
    // 跟踪回复查询
    public PageInfo<Map<String, Object>> getVistList(int currenPage, ClientVO vo);

    // 分配查询
    public PageInfo<Map<String, Object>> getAssignList(int currenPage, ClientVO vo);

    public PageInfo<Map<String, Object>> getAssignByManager(int currenPage, ClientVO vo);

    public PageInfo<Map<String, Object>> getClientList(int currenPage, ClientVO vo);

    // 查询页面查询
    public PageInfo<Map<String, Object>> getSearchList(int currenPage, ClientVO vo);

    // add by cj 回收站线索查询
    public PageInfo<Map<String, Object>> getSearchList1(int currenPage, ClientVO vo);

    // 查找报表界面表头
    public List<Map<String, Object>> getReportHead();

    // add by cj 加入合计的标头
    public List<Map<String, Object>> getReportHeadForExcel();

    // 查找报表界面内容
    public List<Map<String, Object>> getCountAll(Map<String, Object> paras);

    /**
     * @author 陈继龙 E-mail: cqcnihao@139.com @Title: exportExcel @param @param
     *         paras @param @return 设定文件 @return byte [] 返回类型 @throws
     */
    public byte[] exportExcel(Map<String, Object> paras) throws IOException;

    // add by cj 线索录入统计
    public List<Map<String, Object>> getTotal(Map<String, Object> paras, boolean flag);

    // add by cj 查询回复数
    public List<Map<String, Object>> getHfCount(Map<String, Object> paras);

    // add by liangds 2016-01-20
    public Map<String, String> hastenTask(String taskId);

    public List<Map<String, Object>> analyzeRepleat(String organ_id, String stime, String etime);

    // add by cj 获取线索总数
    public String getClientCount();

    // 查询页面查询
    public byte[] getSearchListOfExcel(ClientVO vo) throws Exception;

    // add by chenjilong 2016-01-25 催促 保存到数据库
    public Map<String, String> hastenTask(String taskId, AuthorUser user) throws Exception;

    // ADD BY CJ 查询定制任务
    public PageInfo<Map<String, Object>> getPlanList(int currenPage, Map<String, Object> paras);

    // ADD BY CJ 查询当月任务
    public Map<String, Object> getPlan(Map<String, Object> paras);

    // add by cj更新当月任务
    public int updatePlan(Map<String, Object> result, Map<String, Object> paras);

    // add by cj更新当月任务
    public String getTaskState(Map<String, Object> paras);

    // add by cj 获取客户列表
    public List<Map<String, Object>> getTimeoutList(Map<String, Object> paras);

    // add by cj 获取加分减分
    public Object[] getScore(Map<String, Object> paras);

    public int updateTaskByID(long id);

    public String getTotalScore(Map<String, Object> paras);

    public int setScore(ClientVO vo, long user_id, String rankbefore);

    public int isInitPlan(ClientVO vo, long user_id);

    public int initPlan(ClientVO vo, long user_id);

    public String[] getU_TCount(Map<String, Object> paras);

    public String getIsExistsOfClient(String connum);

    public void insertAllocationItem(Object... obj);

    public String processClient(AuthorUser user, VistBean vistBean);

    public byte[] exportToClient(Map<String, Object> paras) throws Exception;

    public List<Map<String, Object>> getExcelData(Map<String, Object> paras) throws Exception;

    public byte[] exportReport(String stime, String etime, String organId) throws Exception;

    public List<Map<String, Object>> getDDReport(Map<String, Object> paras);

    public List<Map<String, Object>> getDDRank(Map<String, Object> paras);

    public List<Map<String, Object>> getDDStatistics(Map<String, Object> paras);

    public List<Map<String, Object>> getDDStatisticsByTime(Map<String, Object> paras);

    public List<Map<String, Object>> getServiceTrace(ClientVO clientVO);

    public String processClientAndTrace(AuthorUser user, ClientVO clientVo, ClientTraceVO clientTraceVo);

    public ActivityConfigVO getActivityConf(Long id);

    public CodeItemVO findOneCodeType(long codeItemId);

    public String findSourceNameByCode(String code);

    public List<Map<String, Object>> queryShortTimeCustomerList(long rid);

    public String dialout(AuthorUser currenUser, String tel) throws Exception;
    
    public String processToErp(AuthorUser user,ClientVO vo) throws Exception;
}
