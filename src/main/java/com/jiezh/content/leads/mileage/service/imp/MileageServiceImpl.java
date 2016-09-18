package com.jiezh.content.leads.mileage.service.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.content.leads.mileage.service.MileageService;
import com.jiezh.content.leads.mileage.util.ExcelUtil;
import com.jiezh.dao.leads.mileage.dao.MileageDao;
import com.jiezh.dao.leads.mileage.vo.CarRentelVO;
import com.jiezh.dao.leads.mileage.vo.CustomerVO;
import com.jiezh.dao.leads.mileage.vo.MileageVO;
import com.jiezh.dao.leads.mileage.vo.QuestionVO;
import com.jiezh.dao.leads.mileage.vo.SatisfactionVO;

/**
 * 客户里程与满意度
 * 
 * @className MileageServiceImpl
 * @author JXY
 * @version V1.0
 */
@Service("leads.mileage.service.MileageService")
public class MileageServiceImpl implements MileageService {

    Log log = LogFactory.getLog(MileageServiceImpl.class);

    @Autowired
    public MileageDao mileageDao;

    /**
     * 分页查询数据列表
     */
    public PageInfo<CustomerVO> queryCustomerMileageList(int currentPage, CustomerVO customerVO) throws Exception {
        try {
            PageHelper.startPage(currentPage, Env.PAGE_SIZE);
            Page<CustomerVO> page = (Page<CustomerVO>) mileageDao.queryCustomerMileageList(customerVO);
            if (page == null) {
                return new PageInfo<CustomerVO>();
            }
            return new PageInfo<CustomerVO>(page);
        } catch (Exception e) {
            log.error("执行查询SQL出错！", e);
            throw new Exception("查询数据时发生错误！");
        }

    }

    /**
     * 查询需要导出的数据
     * 
     * @param idList
     * @return
     * @throws Exception
     */
    public List<CustomerVO> queryCustomerMileageList(CustomerVO queryVO) throws Exception {
        try {
            List<CustomerVO> dataList = mileageDao.queryMatchingCustomerMileageList(queryVO);
            return dataList == null ? new ArrayList<CustomerVO>() : dataList;
        } catch (Exception e) {
            log.error("执行查询SQL出错！", e);
            throw new Exception("导出失败，处理导出数据时发生错误！");
        }
    }

    /**
     * 查看客户满意度详情
     */
    public Map<String, Object> queryCustomerMileageDetail(Long customerid) throws Exception {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            CustomerVO customerVO = mileageDao.queryCustomerMileageDetail(customerid);
            List<QuestionVO> questionList = mileageDao.querySatisfactionDetail(customerid);
            map.put("customerVO", customerVO == null ? new CustomerVO() : customerVO);
            map.put("questionList", questionList == null ? new ArrayList<SatisfactionVO>() : questionList);
            return map;
        } catch (Exception e) {
            log.error("执行查询SQL出错！", e);
            throw new Exception("查询当前用户的满意度详细信息时发生错误！");
        }
    }

    /**
     * 更改客户提醒状态
     */
    public String updateRemind(String[] mids) throws Exception {
        List<MileageVO> mileageList = new ArrayList<MileageVO>(10);
        for (String mid : mids) {
            if (StringUtils.isNotBlank(mid)) {
                MileageVO mileageVO = new MileageVO();
                mileageVO.setId(Long.valueOf(mid.split(":")[0]));
                mileageVO.setIsremind(mid.split(":")[1].equals("否") ? "1" : "0");
                mileageList.add(mileageVO);
            }
        }
        if (mileageList.size() > 0) {
            mileageDao.updateRemind(mileageList);
        }
        return "更改提醒状态成功！";
    }

    /**
     * 数据导出
     */
    public byte[] createExcel(CustomerVO queryVO) throws Exception {
        String sheetName = "客户里程与满意度情况表";
        // 创建Excel标题列
        List<String> titles = buildExcelTitles();
        Map<Integer, Integer> questionMap = buildQuestionTitles();
        // 设置EXCEL指定列宽
        Map<Integer, Integer> map = setColumnWidth();
        // 将数据设置到EXCEL表格中
        List<Map<Integer, Object>> rowList = new ArrayList<Map<Integer, Object>>();// Excel表格数据
        List<CustomerVO> dataList = queryCustomerMileageList(queryVO); // 原始数据
        for (int i = 0; i < dataList.size(); i++) {
            Map<Integer, Object> cellMap = new HashMap<Integer, Object>();
            CustomerVO cvo = dataList.get(i);
            // int mileage = cvo.getCarRentelVO().getMileageVO().getMileage().intValue();
            // int lastMileage = cvo.getCarRentelVO().getMileageVO().getLastmileage().intValue();
            cellMap.put(0, i + 1);
            cellMap.put(1, cvo.getSubsidiary());
            cellMap.put(2, cvo.getStore());
            cellMap.put(3, cvo.getCustomername());
            cellMap.put(4, cvo.getTel());

            cellMap.put(5, cvo.getCarRentelVO().getCarvin());
            cellMap.put(6, cvo.getCarRentelVO().getBrandtype());
            cellMap.put(7, cvo.getCarRentelVO().getMileageVO().getMileage());
            cellMap.put(8, cvo.getCarRentelVO().getDeliverdate());

            cellMap.put(9, cvo.getInvestigator());
            cellMap.put(10, cvo.getInvestigationdate());
            cellMap.put(11, cvo.getConnectstatus());
            cellMap.put(12, cvo.getInvestigatorsex().equals("0") ? "女" : "男");

            List<SatisfactionVO> questionList = cvo.getQuestionList();
            if (questionList != null && questionList.size() > 0) {
                for (int j = 0; j < questionList.size(); j++) {
                    SatisfactionVO svo = questionList.get(j);
                    Set<Map.Entry<Integer, Integer>> set = questionMap.entrySet();
                    for (Map.Entry<Integer, Integer> entry : set) {
                        int colidx = entry.getKey().intValue();
                        // 将对应的问题放在对应的列
                        if (entry.getValue().intValue() == svo.getQuestionid().intValue()) {
                            cellMap.put(entry.getKey(), svo.getAnswer());
                            if (colidx == 13 || colidx == 19 || colidx == 21) {
                                cellMap.put(colidx + 1, svo.getDetailanswer());
                            }
                        }
                    }
                }
            }
            rowList.add(cellMap);
        }
        return ExcelUtil.export2Excel(sheetName, titles, rowList, map);
    }

    /**
     * 数据导入
     */
    public Map<String, Object> importExcel(InputStream in, String fileName) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put(Env.ROW_START_KEY, 2);
            paraMap.put(Env.COL_START_KEY, "B");
            paraMap.put(Env.COL_END_KEY, "Z");
            // 获取导入Excel数据
            List<List<Object>> dataList = ExcelUtil.importFromExcel(in, fileName, paraMap);
            in.close();
            // 保存导入数据
            if (dataList == null || dataList.size() == 0) {
                throw new Exception("没有获取到有效的导入数据！");
            }
            saveImportDatas(dataList);
            dataMap.put("page", queryCustomerMileageList(1, new CustomerVO()));
            return dataMap;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    // 处理导入数据，保存到数据库
    public void saveImportDatas(List<List<Object>> rowList) throws Exception {
        try {
            for (int x = 0; x < rowList.size(); x++) {
                Map<String, Object> paraMap = new HashMap<String, Object>();
                boolean userExist = false; // 标示用户是否已导入过
                List<Object> cellList = rowList.get(x);
                paraMap.put("tel", (String) cellList.get(3));
                paraMap.put("carvin", (String) cellList.get(4));
                // 根据手机号和车架号判断该用户是否已导入过
                CustomerVO customerVO = mileageDao.queryCarMileageByCustomertel(paraMap);
                if (customerVO != null) {
                    userExist = true;
                } else {
                    customerVO = new CustomerVO();
                }
                // 保存导入用户信息
                customerVO.setSubsidiary((String) cellList.get(0)); // 子公司
                customerVO.setStore((String) cellList.get(1)); // 门店
                customerVO.setCustomername((String) cellList.get(2)); // 申请人
                customerVO.setTel((String) cellList.get(3)); // 电话
                customerVO.setInvestigator((String) cellList.get(8)); // 调研人
                customerVO.setInvestigationdate(convertDate((String) cellList.get(9), x + 1, 11)); // 调研日期
                customerVO.setConnectstatus((String) cellList.get(10)); // 接通状态
                customerVO.setInvestigatorsex(((String) cellList.get(11)).equals("女") ? "0" : "1"); // 性别
                if (userExist) {
                    // 更新用户信息
                    mileageDao.updateCustomerInfo(customerVO);
                } else {
                    // 插入用户信息
                    mileageDao.insertCustomerInfo(customerVO);
                }

                // 保存导入租用车辆信息
                CarRentelVO carRentelVO = new CarRentelVO();
                if (userExist) {
                    carRentelVO.setId(customerVO.getCarRentelVO().getId());
                    carRentelVO.setMileageVO(customerVO.getCarRentelVO().getMileageVO());
                }
                carRentelVO.setCustomerid(customerVO.getId()); // 客户ID
                carRentelVO.setCustomertel((String) cellList.get(3)); // 客户电话
                carRentelVO.setCarvin((String) cellList.get(4)); // 车架号
                carRentelVO.setBrandtype((String) cellList.get(5)); // 品牌型号
                carRentelVO.setDeliverdate(convertDate((String) cellList.get(7), x + 1, 9)); // 交车日期
                if (userExist) {
                    // 更新车辆信息
                    mileageDao.updateCarRentelInfo(carRentelVO);
                } else {
                    // 插入车辆信息
                    mileageDao.insertCarRentelInfo(carRentelVO);
                }

                // 保存最新行驶里程并记录上次行驶里程
                Integer curr = convertInteger((String) cellList.get(6), x + 1, 8);
                MileageVO mileageVO = new MileageVO();
                mileageVO.setCarid(carRentelVO.getId()); // 车辆ID
                mileageVO.setMileage(curr); // 行驶里程
                mileageVO.setLastmileage(carRentelVO.getMileageVO() == null ? 0 : carRentelVO.getMileageVO().getMileage()); // 上次行驶里程
                mileageVO.setRemindtimes((curr / 5000) == 0 ? 1 : (curr / 5000) - 1); // 设置提醒次数
                mileageVO.setIsremind("0"); // 是否已提醒
                mileageDao.insertMileageInfo(mileageVO);

                // 保存导入满意度调查信息
                int questionid = 0;
                List<SatisfactionVO> questionList = new ArrayList<SatisfactionVO>();
                for (int i = 12; i < cellList.size(); i++) { // 从调查问题列开始，循环到最后一列
                    questionid++;
                    String answer = (String) cellList.get(i);
                    if (StringUtils.isBlank(answer)) {
                        if (i == 12 || i == 18 || i == 20) {
                            ++i;
                        }
                        continue;
                    } else {
                        SatisfactionVO satisfactionVO = new SatisfactionVO();
                        satisfactionVO.setCustomerid(customerVO.getId()); // 客户ID
                        satisfactionVO.setCustomertel(customerVO.getTel()); // 客户电话
                        satisfactionVO.setQuestionid(questionid); // 问题ID
                        satisfactionVO.setAnswer(answer.toUpperCase()); // 问题答案
                        if (i == 12 || i == 18 || i == 20) { // 对有扩展的问题记录对应内容，并跳过扩展列的遍历
                            satisfactionVO.setDetailanswer((String) cellList.get(++i)); // 问题答案描述
                        }
                        questionList.add(satisfactionVO);
                    }
                }
                if (questionList.size() > 0) {
                    if (userExist) {
                        mileageDao.updateSatisfactionInfo(questionList);
                    } else {
                        mileageDao.batchInsertSatisfactionInfo(questionList);
                    }
                }
            }
        } catch (Exception e) {
            log.error("保存导入数据到数据库时发生错误！", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(StringUtils.isBlank(e.getMessage()) ? "导入文件数据与模板数据不一致，请重新导入！" : e.getMessage());

        }

    }

    // 数据导出-设置标题列
    private List<String> buildExcelTitles() {
        List<String> titles = new ArrayList<String>(30);
        titles.add("序号");
        titles.add("子公司");
        titles.add("门店");
        titles.add("申请人");
        titles.add("电话");
        titles.add("车架号");
        titles.add("品牌型号");
        titles.add("行驶里程");
        titles.add("交车日期");
        titles.add("调研人");
        titles.add("调研日期");
        titles.add("是否接通");
        titles.add("性别");
        titles.add("问题一");
        titles.add("问题一扩展");
        titles.add("问题二");
        titles.add("问题三");
        titles.add("问题四");
        titles.add("问题五");
        titles.add("问题六");
        titles.add("问题六扩展");
        titles.add("问题七");
        titles.add("问题七扩展");
        titles.add("问题八");
        titles.add("问题九");
        titles.add("问题十");
        return titles;
    }

    // 数据导出-设置问题答案显示列
    private Map<Integer, Integer> buildQuestionTitles() {
        Map<Integer, Integer> questionMap = new HashMap<Integer, Integer>();
        questionMap.put(13, 1);
        questionMap.put(15, 2);
        questionMap.put(16, 3);
        questionMap.put(17, 4);
        questionMap.put(18, 5);
        questionMap.put(19, 6);
        questionMap.put(21, 7);
        questionMap.put(23, 8);
        questionMap.put(24, 9);
        questionMap.put(25, 10);
        return questionMap;
    }

    // 数据导出-设置导出列宽
    private Map<Integer, Integer> setColumnWidth() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 10);
        map.put(1, 15);
        map.put(2, 20);
        map.put(3, 15);
        map.put(4, 20);
        map.put(5, 30);
        map.put(6, 20);
        map.put(7, 20);
        map.put(8, 20);
        map.put(9, 15);
        map.put(10, 20);
        map.put(11, 20);
        map.put(12, 10);
        map.put(13, 10);
        map.put(14, 50);
        map.put(15, 10);
        map.put(16, 10);
        map.put(17, 10);
        map.put(18, 10);
        map.put(19, 10);
        map.put(20, 50);
        map.put(21, 10);
        map.put(22, 50);
        map.put(23, 10);
        map.put(24, 10);
        map.put(25, 50);
        return map;
    }

    public static int convertInteger(String str, int rownum, int colnum) throws Exception {
        try {
            int value = 0;
            if (StringUtils.isNotBlank(str)) {
                value = Integer.parseInt(str);
            }
            return value;
        } catch (Exception e) {
            throw new Exception("导入数据中第" + rownum + "行第" + colnum + "列应当为数字类型的数据！");
        }
    }

    public static Date convertDate(String str, int rownum, int colnum) throws Exception {
        try {
            if (StringUtils.isNotBlank(str)) {
                Date value = DateUtil.toDate(str);
                if (value == null) {
                    throw new Exception();
                }
                return value;
            }
            return null;
        } catch (Exception e) {
            throw new Exception("导入数据中第" + rownum + "行第" + colnum + "列的日期格式不正确！");
        }
    }

}
