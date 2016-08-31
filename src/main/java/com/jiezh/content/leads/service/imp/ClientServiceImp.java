package com.jiezh.content.leads.service.imp;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.base.pub.util.DaoUtil;
import com.jiezh.content.base.pub.util.DateUtil;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.pub.util.Tools;
import com.jiezh.content.base.weixin.tools.Parameters;
import com.jiezh.content.base.weixin.tools.WeixinConfig;
import com.jiezh.content.base.weixin.tools.WeixinUtil;
import com.jiezh.content.base.weixin.tools.bean.News;
import com.jiezh.content.leads.exchange.ExchangeBean;
import com.jiezh.content.leads.exchange.QueueTools;
import com.jiezh.content.leads.search.web.ExcelUtil;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.content.leads.vist.web.VistBean;
import com.jiezh.dao.base.user.UserVO;
import com.jiezh.dao.leads.activity.ActivityConfigVO;
import com.jiezh.dao.leads.activity.ActivityConfigVODao;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.client.ClientTraceDao;
import com.jiezh.dao.leads.client.ClientTraceVO;
import com.jiezh.dao.leads.client.ClientVO;
import com.jiezh.dao.leads.client.HfBeanVO;
import com.jiezh.dao.leads.client.update.ClientUpdateDao;
import com.jiezh.dao.leads.client.update.ClientUpdateVO;
import com.jiezh.dao.leads.client.urge.LmurgeVO;
import com.jiezh.dao.leads.client.urge.LmurgeVODao;

@Service("leads.service.BaseClientService")
public class ClientServiceImp implements ClientService {
    @Autowired
    ClientDao clientDao;
    @Autowired
    ClientTraceDao clientTraceDao;
    @Autowired
    LmurgeVODao lmurgeVODao;
    @Autowired
    ClientUpdateDao clientUpdateDao;
    @Autowired
    ActivityConfigVODao activityConfigVODao;
    final static ReentrantLock lock = new ReentrantLock();

    public int addClient(ClientVO recode) throws Exception {
        recode.setSex("1");
        recode.setQdate(new Date());
        if (recode.getSid() != 0) {
            recode.setDdate(new Date());
        }
        int id = clientDao.insert(recode);
        if (null != recode.getTel()) {
            int numtel = clientDao.selectTel(recode.getTel());
            if (numtel > 1) {
                throw new Exception("手机号: " + recode.getTel() + " 已经存在，请勿重复录入！");
            }
        }
        ClientUpdateVO clientUpdateVO = new ClientUpdateVO();

        clientUpdateVO.setClientId(recode.getId());
        clientUpdateVO.setField("title");
        clientUpdateVO.setFieldBefore("");
        clientUpdateVO.setFieldAfter(recode.getTitle());
        clientUpdateVO.setUpdateIndex(UUID.randomUUID().toString());
        clientUpdateVO.setCreatedUserId((long) recode.getRid());
        clientUpdateDao.insert(clientUpdateVO);
        return id;
    }

    public PageInfo<Map<String, Object>> search(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, Env.PAGE_SIZE);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) clientDao.selectClient(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);
    }

    @Override
    public String querySub(String pid) {
        List<Map<String, String>> listTree = clientDao.querySub(pid);

        String str4 = "<select name=\"smallPid\" id=\"smallPid\" >";
        str4 = str4 + "<option value=\"\">请选择</option>";
        StringBuffer stree = new StringBuffer();
        for (Map<String, String> obj : listTree) {
            // 样式 <select name="dee"> <option value="323s" selected>zkk</option>
            // </select>
            stree.append(" <option value=\"" + obj.get("ID") + "\">" + obj.get("TITLE") + "</	option>");
        }
        str4 = str4 + stree.toString();
        str4 = str4 + "</select>";
        return str4;
    }

    @Override
    public String queryOrgPerson(String from, String organId, Long userId, String organCode) {
        List<Node> nodes = new ArrayList<Node>();

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("organId", organId);
        param.put("organCode", organCode);
        Node node = null;
        List<Map<String, Object>> listR = null;
        // 得到列表
        // 如果是从addClient页面进来。区分是总公司管理员的进入另一个查询，查询所有管理员 1.21去掉这个约束
        // if("addClient".equals(from) && "00".equals(organId))
        // listR = clientDao.queryOrgPersonByRole(param);
        // else
        // listR = clientDao.queryOrgPerson(param);

        // 如果是从forAssign页面进来。把是管理员的查询出来。。 1-25
        if ("forAssign".equals(from)) listR = clientDao.queryOrgPersonManager(param);
        else listR = clientDao.queryOrgPerson(param);
        for (Map<String, Object> obj : listR) {
            node = new Node();
            node.setId(obj.get("ID").toString());
            node.setName(obj.get("NAME").toString());
            node.setpId(obj.get("PID").toString());

            // 为了跟机构区分ID。所以在人员查询的时候加上了**
            if (obj.get("ID").toString().contains("*")) node.setIsParent(false);
            else node.setIsParent(true);
            node.setOpen(false);

            if (obj.get("ID").toString().contains("*") && obj.get("ID").toString().contains(userId.toString())) node.setChecked(true);

            if (obj.get("CHE").equals("check")) node.setNocheck(false);
            else node.setNocheck(true);
            node.setChecked(false);
            // System.out.println(node.getId()+node.getName()+node.getPid()+node.getT());
            nodes.add(node);
        }

        String str0 = Tools.listToJson(nodes);
        return str0;
    }

    @Override
    public int updateClient(ClientVO recode, AuthorUser user) throws Exception {
        ClientVO old = clientDao.selectByPrimaryKey(recode.getId());
        Class<?> cls = recode.getClass();
        String uuid = UUID.randomUUID().toString();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
            Method getMethod = pd.getReadMethod();
            Object oldField = getMethod.invoke(old) == null ? "" : getMethod.invoke(old);
            Object newField = getMethod.invoke(recode) == null ? "N" : getMethod.invoke(recode);
            if (!oldField.toString().equals(newField.toString()) && !newField.toString().equals("N") && !field.getName().equals("rid")
                && !field.getName().equals("allotdate")) {
                ClientUpdateVO clientUpdateVO = new ClientUpdateVO();

                clientUpdateVO.setClientId(recode.getId());
                clientUpdateVO.setField(field.getName());
                clientUpdateVO.setFieldBefore(oldField.toString());
                clientUpdateVO.setFieldAfter(newField.toString());
                clientUpdateVO.setUpdateIndex(uuid);
                clientUpdateVO.setCreatedUserId(user.getUserId());
                clientUpdateDao.insert(clientUpdateVO);
            }
        }
        if (recode.getSid() != 0) {
            recode.setDdate(new Date());
        }

        return clientDao.updateByPrimaryKey(recode);
    }

    /**
     * 查询待分配的任务
     */
    public List<Map<String, Object>> getClientAssginTask(List<String> list) {
        return clientDao.getClientInfoByIds(list);
    }

    /**
     * 更新用户
     */
    public int updateTaskUser(List<String> ids, String newUserId, String newUserOrganId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("newUserId", newUserId);
        param.put("newUserOrganId", newUserOrganId);
        param.put("ids", ids);
        return clientDao.updateAssign(param);
    }

    /**
     * 线索删除
     */
    public int delData(List<String> ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return clientDao.delData(param);
    }

    /**
     * 线索恢复
     */
    public int renewData(List<String> ids) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ids", ids);
        return clientDao.renewData(param);
    }

    /**
     * 查询追踪
     */
    @Override
    public List<Map<String, Object>> searchTrace(ClientVO clientVO) {
        return clientDao.searchTrace(clientVO);
    }

    /**
     * 查询追踪客户信息
     */
    @Override
    public Map<String, Object> searchClient(ClientVO clientVO) {
        Map<String, Object> map = clientDao.selectClient(clientVO).get(0);
        return map;
    }

    /**
     * 保存追踪客户
     */
    @Override
    public void saveTrace(ClientVO clientVO, ClientTraceVO clientTraceVO) {
        clientDao.updateByPrimaryKey(clientVO);
        clientTraceDao.insert(clientTraceVO);
    }

    /**
     * 查询客户
     */
    @Override
    public ClientVO searchVo(String clID) {
        return clientDao.selectByPrimaryKey(Long.parseLong(clID));
    }

    /**
     * 跟踪回复查询
     */
    public PageInfo<Map<String, Object>> getVistList(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, 20);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) clientDao.getVistList(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);
    }

    /**
     * 分配查询
     * 
     */
    public PageInfo<Map<String, Object>> getAssignList(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, Env.PAGE_SIZE);

        Page<Map<String, Object>> page = null;
        // 电话优先。。1.12
        if (clientVO.getTel() != null && clientVO.getTel().length() == 11) page = (Page<Map<String, Object>>) clientDao.getAssignListByTel(clientVO);
        else page = (Page<Map<String, Object>>) clientDao.getAssignList(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);
    }

    /**
     * 管理员分配查询
     */
    public PageInfo<Map<String, Object>> getAssignByManager(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, 20);

        Page<Map<String, Object>> page = null;
        // 电话优先。。1.12
        if (clientVO.getTel() != null && clientVO.getTel().length() == 11) page = (Page<Map<String, Object>>) clientDao.getAssignListByTel(clientVO);
        else page = (Page<Map<String, Object>>) clientDao.getAssignByManager(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);

    }

    /**
     * 查询处理的人
     */
    @Override
    public String searchPerson(String clID) {
        return clientDao.searchPerson(Long.parseLong(clID));
    }

    /**
     * 异步校验电话
     */
    @Override
    public String checkTel(String tel) {
        String re = "";
        if (clientDao.checkTel(tel) != null && clientDao.checkTel(tel).length() > 0) re = "电话号码已经存，请修改！";
        return re;
    }

    /**
     * 客服录入查询
     */
    public PageInfo<Map<String, Object>> getClientList(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, Env.PAGE_SIZE);

        Page<Map<String, Object>> page = null;
        // 电话优先。。1.12
        if (clientVO.getTel() != null && clientVO.getTel().length() == 11) page = (Page<Map<String, Object>>) clientDao.getClientListByTel(clientVO);
        else page = (Page<Map<String, Object>>) clientDao.getClientList(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);

    }

    /**
     * 查询
     */
    public PageInfo<Map<String, Object>> getSearchList(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, Env.PAGE_SIZE);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) clientDao.getSearchList(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);

    }

    /**
     * 回收站线索查询
     */
    public PageInfo<Map<String, Object>> getSearchList1(int currenPage, ClientVO clientVO) {
        PageHelper.startPage(currenPage, Env.PAGE_SIZE);
        Page<Map<String, Object>> page = (Page<Map<String, Object>>) clientDao.getSearchList1(clientVO);
        if (page == null) {
            page = new Page<Map<String, Object>>();
        }
        return new PageInfo<Map<String, Object>>(page);

    }

    /**
     * 查找报表界面表头
     */
    @Override
    public List<Map<String, Object>> getReportHead() {
        return clientDao.getReportHead();
    }

    /**
     * add by cj 加入合计的表头
     */
    @Override
    public List<Map<String, Object>> getReportHeadForExcel() {
        return clientDao.getReportHeadForExcel();
    }

    /**
     * 查找报表界面内容
     */
    @Override
    public List<Map<String, Object>> getCountAll(Map<String, Object> paras) {

        // modify by cj 来源报表统计线索总和
        List<Map<String, Object>> countList = clientDao.getCountAll(paras);
        int count = 0;
        for (Map<String, Object> map : countList) {
            count += Integer.parseInt(map.get("MADD") + "");
        }
        Map<String, Object> countMap = new HashMap<String, Object>();
        countMap.put("count", count);
        countList.add(countMap);

        return countList;
    }

    /***
     * @author 陈继龙 E-mail: cqcnihao@139.com
     * @date 2015年12月29日 下午2:58:32
     *       <p>
     *       Title: exportExcel
     *       </p>
     *       <p>
     *       Description:
     *       </p>
     * @param paras
     * @return
     * @throws IOException
     * @see com.jiezh.content.leads.service.ClientService#exportExcel(java.util.Map)
     */
    @SuppressWarnings("deprecation")
    @Override
    public byte[] exportExcel(Map<String, Object> paras) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        wb.setCompressTempFiles(true);
        int rowIndex = 2;
        int cellLength = 0;

        // add by cj 新增计数器
        int result = 0;
        // create sheet
        Sheet sheet = wb.createSheet("报表" + DateUtil.date2String(new Date(), "yyyy-MM-dd"));
        /**
         * 得到表头信息和报表表格信息
         **/
        List<Map<String, Object>> listHead = this.getReportHeadForExcel();
        List<Map<String, Object>> listBooy = this.getCountAll(paras);

        // 删除总线索量
        listBooy.remove(listBooy.size() - 1);

        result = listHead.size();
        // add by cj 处理list body X轴
        // listBooy = excuteBodyList_x(listBooy);
        // add by cj y轴关系映射表
        Map<String, Integer> mapping = excuteBodyList_y(listBooy);
        // add by cj 处理Excel head信息
        listHead = excuteBodyList_head(listHead, mapping);
        cellLength = listHead.size();
        Map<Integer, Integer> mp = getMapping(listBooy);
        // 全部都为0时返回空文件
        if (listHead.size() < 4) {
            // return new byte[]{};
            setEmpInfo(wb, sheet);
            wb.write(out);
            return out.toByteArray();
        }
        setTitle(wb, sheet, cellLength, paras);
        setHeader(wb, sheet, listHead);
        int sign = 2; // 标记开始行号
        int samerow = 0; // 相同行的计数器
        int tep = 0;
        if (listBooy.size() > 0) {
            // create row
            for (int rowi = 0; rowi <= listBooy.size(); rowi++) {
                if (rowi == listBooy.size()) { // 处理 末尾合并
                    sheet.addMergedRegion(new CellRangeAddress(sign, sign + samerow - 1, (short) 0, (short) 0));
                    // add by cj
                    // sheet.addMergedRegion(new CellRangeAddress(sign, sign +
                    // samerow - 1, cellLength, cellLength)); // 合并列
                    continue;
                }
                Map<String, Object> obj = (Map<String, Object>) listBooy.get(rowi);
                Row row = sheet.createRow(rowIndex);
                int organId = Integer.valueOf(obj.get("ORGAN_ID").toString()).intValue();
                if (tep != organId) {
                    if (samerow > 0) {
                        sheet.addMergedRegion(new CellRangeAddress(sign, sign + samerow - 1, (short) 0, (short) 0)); // 合并列
                        // add by cj
                        // sheet.addMergedRegion(new CellRangeAddress(sign, sign
                        // + samerow - 1, cellLength, cellLength)); // 合并列

                    }
                    sign = rowIndex;
                    tep = organId;
                    samerow = 1;
                } else {
                    samerow++; // 相同加1
                }

                // create cells
                for (int i = 0; i < result; i++) {
                    if (i == 0) {
                        row.createCell(i).setCellValue(obj.get("NAME").toString());
                    } else if (i == 1) {
                        row.createCell(i).setCellValue(obj.get("TITLE").toString());
                    } else {

                        int h = 1;
                        for (int y = 1; y <= result - 2; y++) {
                            // add by cj 增加合计 KEY=TOTAL 出现在每行最后一个CELL
                            if (y == result - 2) {
                                // HSSFRichTextString hts = new
                                // HSSFRichTextString(obj.get("MADD").toString());
                                Cell cell = row.createCell(h + 1);
                                cell.setCellValue(Integer.parseInt(obj.get("MADD") + ""));
                                CellStyle cellStyle = wb.createCellStyle();
                                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                                cell.setCellStyle(cellStyle);
                                continue;
                            }
                            if (mapping.get("M" + y) == 0) {
                                continue;
                            }

                            // HSSFRichTextString hts = new
                            // HSSFRichTextString(obj.get("M" + y).toString());
                            Cell cell = row.createCell(h + 1);
                            cell.setCellValue(Integer.parseInt(obj.get("M" + y) + ""));
                            CellStyle cellStyle = wb.createCellStyle();
                            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                            cell.setCellStyle(cellStyle);
                            h++;
                        }
                        // add by cj 分公司合计
                        // Cell cell = row.createCell(cellLength);
                        // cell.setCellValue(mp.get(Integer.parseInt(obj.get("ORGAN_ID")+"")));
                    }
                }
                rowIndex++;
            }
        }
        // add by cj
        setTotalInfo(wb, sheet, rowIndex, mapping);
        wb.write(out);
        return out.toByteArray();
    }

    private void setHeader(SXSSFWorkbook workbook, Sheet sheet, List<Map<String, Object>> list) {
        Font headFont = workbook.createFont();
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setFont(headFont);
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 设置上边框
        headCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 设置下边框
        headCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 设置做边框
        headCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 设置右边框
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置一个单元格边框颜色
        headCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 背景颜色
        headCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headCellStyle.setFillPattern(IndexedColors.BLUE_GREY.getIndex());

        // create header
        Row header = sheet.createRow(1);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> obj = (Map<String, Object>) list.get(i);
            // {NAME=58同城, PID=0, ID=1}
            String strName = (String) obj.get("NAME");
            Cell cell = header.createCell(i);
            cell.setCellValue(strName);
            cell.setCellStyle(headCellStyle);
        }

    }

    private void setTitle(SXSSFWorkbook workbook, Sheet sheet, int cellLength, Map<String, Object> paras) {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        // String strTitle = "统计区间："+paras.get("stime")+"至"+paras.get("etime")
        // +" 导出时间："+ DateUtil.date2String(date, "yyyy-MM-dd HH:mm:ss") + " " +
        // dateFm.format(date);

        // modify by cj 修改title显示
        String strTitle = getTitle(paras);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 25);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // titleStyle.setFillPattern(XSSFCellStyle.BORDER_MEDIUM_DASHED); //背景颜色
        titleStyle.setLocked(true);
        titleStyle.setWrapText(true);

        Row headertitle = sheet.createRow(0);
        headertitle.setHeightInPoints(50);
        Cell titleCell = headertitle.createCell(0);
        titleCell.setCellValue(new HSSFRichTextString(strTitle));
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, (short) cellLength - 1));
    }

    @Override
    public List<Map<String, Object>> getTotal(Map<String, Object> paras, boolean flag) {
        return flag ? clientDao.getTotalAll(paras) : clientDao.getTotal(paras);
    }

    @Override
    public List<Map<String, Object>> getHfCount(Map<String, Object> paras) {
        // 获取全部用户
        List<Map<String, Object>> users = clientDao.getAllUser(paras);
        // 获取全部机构
        List<Map<String, Object>> organ = clientDao.getAllOrgan(paras);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> totalPer = clientDao.getTotalPer(paras);
        List<Map<String, Object>> reTotalPer = clientDao.getReTotalPer(paras);

        List<Map<String, Object>> totalPerOc = clientDao.getTotalPerOc(paras);
        List<Map<String, Object>> reTotalPerOc = clientDao.getReTotalPerOc(paras);

        Map<String, Object> resultTotalPer = new HashMap<String, Object>();
        Map<String, Object> resultreTotalPer = new HashMap<String, Object>();

        Map<String, List<Map<String, Object>>> resultTotalPerOc = new HashMap<String, List<Map<String, Object>>>();
        Map<String, List<Map<String, Object>>> resultreTotalPerOc = new HashMap<String, List<Map<String, Object>>>();

        // 组装数据
        // 公司总数
        for (Map<String, Object> map : totalPer) {
            resultTotalPer.put(map.get("NAME") + "", map.get("TOTAL"));
        }
        // 公司回复数
        for (Map<String, Object> map : reTotalPer) {
            resultreTotalPer.put(map.get("NAME") + "", map.get("RETOTAL"));
        }
        // 人员总数
        for (Map<String, Object> map : totalPerOc) {
            List<Map<String, Object>> tmp =
                resultTotalPerOc.get(map.get("NAME")) == null ? new ArrayList<Map<String, Object>>() : resultTotalPerOc.get(map.get("NAME"));
            tmp.add(map);
            resultTotalPerOc.put(map.get("NAME") + "", tmp);
        }
        // 人员回复数
        for (Map<String, Object> map : reTotalPerOc) {
            List<Map<String, Object>> tmp =
                resultreTotalPerOc.get(map.get("NAME")) == null ? new ArrayList<Map<String, Object>>() : resultreTotalPerOc.get(map.get("NAME"));
            tmp.add(map);
            resultreTotalPerOc.put(map.get("NAME") + "", tmp);
        }

        for (Map<String, Object> map : organ) {
            Map<String, Object> tmp = new HashMap<String, Object>();
            String organName = map.get("NAME") + "";
            // 计数器
            int index = 0;
            if (resultreTotalPer.containsKey(organName)) {
                int total = Integer.parseInt(resultTotalPer.get(organName) + ""); // 总量
                int retotal = Integer.parseInt(resultreTotalPer.get(organName) + ""); // 回复量
                int nretotal = total - retotal;// 未回复量
                double lv = Double.valueOf(retotal) / Double.valueOf(total); // 回复率
                tmp.put("total", total);
                tmp.put("name", organName);
                tmp.put("nretotal", nretotal);
                tmp.put("retotal", retotal);
                tmp.put("lv", new DecimalFormat("####.##").format(lv * 100) + "%");

                // 添加子信息
                List<Object> tmpResult = new ArrayList<Object>();
                for (Map<String, Object> mapUser : users) {
                    if (!mapUser.get("NAME").equals(organName)) {
                        continue;
                    }

                    int totalTmp = 0; // 总量
                    int retotalTmp = 0; // 回复量
                    for (Map<String, Object> m : resultTotalPerOc.get(organName)) {
                        if (m.get("USER_NAME").equals(mapUser.get("USER_NAME"))) {
                            totalTmp = Integer.parseInt(m.get("TOTAL") == null ? "0" : m.get("TOTAL") + "");
                        }

                    }
                    for (Map<String, Object> m : resultreTotalPerOc.get(organName)) {
                        if (m.get("USER_NAME").equals(mapUser.get("USER_NAME"))) {
                            retotalTmp = Integer.parseInt(m.get("RETOTAL") == null ? "0" : m.get("RETOTAL") + "");
                        }
                    }

                    int nretotalTmp = totalTmp - retotalTmp;// 未回复量
                    double lvTmp = 0.0;
                    if (totalTmp != 0) {
                        lvTmp = Double.valueOf(retotalTmp) / Double.valueOf(totalTmp); // 回复率
                    }
                    HfBeanVO hv = new HfBeanVO();
                    hv.setLv(new DecimalFormat("####.##").format(lvTmp * 100) + "%");
                    hv.setName(mapUser.get("USER_NAME") + "");
                    hv.setTotal(totalTmp + "");
                    hv.setNretotal(nretotalTmp + "");
                    hv.setRetotal(retotalTmp + "");
                    tmpResult.add(hv);
                    index++;
                }
                tmp.put("chi", tmpResult);
                tmp.put("chiSize", index);
                result.add(tmp);

            } else {
                tmp.put("total", "0");
                tmp.put("name", organName);
                tmp.put("nretotal", "0");
                tmp.put("retotal", "0");
                tmp.put("lv", "0%");
                // 添加子信息
                List<Object> tmpResult = new ArrayList<Object>();
                for (Map<String, Object> mapUser : users) {
                    if (!mapUser.get("NAME").equals(organName)) {
                        continue;
                    }
                    HfBeanVO hv = new HfBeanVO();
                    hv.setLv("0%");
                    hv.setName(mapUser.get("USER_NAME") + "");
                    hv.setTotal("0");
                    hv.setNretotal("0");
                    hv.setRetotal("0");
                    tmpResult.add(hv);
                    index++;
                }
                tmp.put("chi", tmpResult);
                tmp.put("chiSize", index);
                result.add(tmp);
            }
        }
        return result;
    }

    // add by liangds 2016-01-20
    public Map<String, String> hastenTask(String taskId) {
        Map<String, String> map = new HashMap<String, String>();
        if (taskId == null || "".equals(taskId)) {
            taskId = "0";
        }
        ClientVO cvo = clientDao.selectByPrimaryKey(Long.parseLong(taskId));
        String msg = "成功催促";
        if (cvo == null) {
            msg = "查找不到相关记录!";
        } else {
            if (cvo.getSid() < 1) {
                msg = "线索未分配给销售，无法催促!";
            } else {
                // 查找该任务销售用户
                UserVO userVo = DaoUtil.instance().cacheDao().getUserByKey(cvo.getSid());
                if (userVo == null) {
                    msg = "销售用户不存在,请联系管理员!";
                } else {
                    QueueTools.put(new ExchangeBean(userVo.getUserId().toString(), taskId));
                    msg = "该条线索已对 " + userVo.getRealName() + " 进行催促!";
                }
            }
        }
        map.put("msg", msg);
        return map;
    }

    // add by cj 处理excel X轴行数据都为0时不显示问题
    private List<Map<String, Object>> excuteBodyList_x(List<Map<String, Object>> list) {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            if (!map.get("MADD").toString().equals("0")) listResult.add(map);
        }
        return listResult;
    }

    // add by cj 处理excel Y轴行数据都为0时不显示问题
    private Map<String, Integer> excuteBodyList_y(List<Map<String, Object>> list) {
        Map<String, Integer> iMap = new LinkedHashMap<String, Integer>();
        for (Map<String, Object> map : list) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                // 只处理基本数据
                if (key.equals("NAME") || key.equals("TITLE") || key.equals("PID") || key.equals("ORGAN_ID")) {
                    continue;
                }
                // 更新映射集合
                int temp = iMap.get(key) == null ? 0 : iMap.get(key);
                int temp1 = Integer.valueOf(map.get(key) + "");
                iMap.put(key, temp + temp1);
            }
        }
        return iMap;
    }

    // add by cj 处理excel head 显示问题
    private List<Map<String, Object>> excuteBodyList_head(List<Map<String, Object>> list, Map<String, Integer> iMap) {
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
        int i = 1;
        if (iMap.isEmpty()) return listResult;
        for (int x = 0; x < list.size(); x++) {
            Iterator<String> it = list.get(x).keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (x < 2 || x == list.size() - 1) {
                    listResult.add(list.get(x));
                } else {
                    int index = iMap.get("M" + i);
                    if (index > 0) {
                        listResult.add(list.get(x));
                    }
                    i++;
                }
            }
        }
        return listResult;
    }

    // add by cj 返回空信息
    private void setEmpInfo(SXSSFWorkbook workbook, Sheet sheet) {
        String strTitle = "统计区间无数据";
        Font titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // titleStyle.setFillPattern(XSSFCellStyle.BORDER_MEDIUM_DASHED); //背景颜色
        titleStyle.setLocked(true);
        titleStyle.setWrapText(true);

        Row headertitle = sheet.createRow(0);
        headertitle.setHeightInPoints(50);
        Cell titleCell = headertitle.createCell(0);
        titleCell.setCellValue(new HSSFRichTextString(strTitle));
        titleCell.setCellStyle(titleStyle);
    }

    // add by cj 返回空信息
    private void setEmpInfo(HSSFWorkbook workbook, Sheet sheet) {
        String strTitle = "统计区间无数据";
        Font titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // titleStyle.setFillPattern(XSSFCellStyle.BORDER_MEDIUM_DASHED); //背景颜色
        titleStyle.setLocked(true);
        titleStyle.setWrapText(true);

        Row headertitle = sheet.createRow(0);
        headertitle.setHeightInPoints(50);
        Cell titleCell = headertitle.createCell(0);
        titleCell.setCellValue(new HSSFRichTextString(strTitle));
        titleCell.setCellStyle(titleStyle);
    }

    /**
     * 通过存过统计回复率
     * 
     * @param organ_id
     * @param stime
     * @param etime
     * @return
     */
    public List<Map<String, Object>> analyzeRepleat(String organ_id, String stime, String etime) {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("in_organ_id", organ_id);
        paras.put("in_start_date", stime);
        paras.put("in_end_date", etime);
        clientDao.analyzeRepleat(paras);
        String queryId = paras.get("in_query_id").toString();
        Map<String, String> smap = new HashMap<String, String>();
        smap.put("queryId", queryId);
        // 1.统计各分公司总数
        List<Map<String, Object>> list = clientDao.getAllReplyOrgan(queryId);
        // 2.通过机构查询各分公司下所有销售
        for (Map<String, Object> item : list) {
            smap.put("organId", item.get("ORGAN_ID").toString());
            item.put("itemList", clientDao.getReplyList(smap));
        }
        return list;
    }

    // add by cj Y轴汇总
    private void setTotalInfo(SXSSFWorkbook workbook, Sheet sheet, int num, Map<String, Integer> map) {

        // 设置红色字体黄色背景
        Font titleFont = workbook.createFont();
        titleFont.setColor(HSSFColor.RED.index);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);

        Row row = sheet.createRow(num);
        row.createCell(0).setCellValue("合计");
        int index = 2;
        int flag = 1;
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (map.get("M" + flag) == null) {
                flag++;
                continue;
            }
            if (map.get("M" + flag) == 0) {
                flag++;
                continue;
            }
            Cell cell = row.createCell(index);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(map.get("M" + flag));
            index++;
            flag++;
        }
        Cell cell = row.createCell(index);
        cell.setCellStyle(titleStyle);
        cell.setCellValue(map.get("MADD"));
    }

    // add by cj 获取Excel表头
    private String getTitle(Map<String, Object> paras) {
        String date1 = paras.get("stime").toString().substring(0, 10);
        String date2 = paras.get("etime").toString().substring(0, 10);

        if (date1.equals(date2)) {
            Date date;
            int week = 0;
            String weekName = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                week = cal.get(Calendar.DAY_OF_WEEK) - 1;
                switch (week) {
                    case 0:
                        weekName = "星期日";
                        break;
                    case 1:
                        weekName = "星期一";
                        break;
                    case 2:
                        weekName = "星期二";
                        break;
                    case 3:
                        weekName = "星期三";
                        break;
                    case 4:
                        weekName = "星期四";
                        break;
                    case 5:
                        weekName = "星期五";
                        break;
                    case 6:
                        weekName = "星期六";
                        break;
                    default:
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "报表统计  " + date1 + " " + weekName;
        } else {
            return "报表统计  " + date1 + " ~ " + date2;
        }
    }

    private Map<Integer, Integer> getMapping(List<Map<String, Object>> listBooy) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Map<String, Object> maps : listBooy) {
            int organId = Integer.valueOf(maps.get("ORGAN_ID").toString()).intValue();
            int tmp = map.get(organId) == null ? 0 : map.get(organId);
            int value = Integer.valueOf(maps.get("MADD").toString()).intValue();
            map.put(organId, value + tmp);
        }

        return map;
    }

    // add by cj 查询线索总数
    @Override
    public String getClientCount() {
        return clientDao.getClientCount();
    }

    @Override
    public byte[] getSearchListOfExcel(ClientVO vo) throws Exception {
        List<Map<String, Object>> map = clientDao.getSearchList(vo);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        SXSSFWorkbook wb = new SXSSFWorkbook(500);
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        wb.setCompressTempFiles(true);
        int rowIndex = 2;
        // create sheet
        Sheet sheet = wb.createSheet("报表" + DateUtil.date2String(new Date(), "yyyy-MM-dd"));

        // 设置标题 和样式 从第二行开始
        Font titleFont = wb.createFont();
        titleFont.setFontName("黑体");
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setBorderBottom((short) 1);
        titleStyle.setBorderLeft((short) 1);
        titleStyle.setBorderRight((short) 1);
        titleStyle.setBorderTop((short) 1);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);

        CellStyle contentStyle = wb.createCellStyle();
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        int index = 0;
        Row rowTitle = sheet.createRow(1);
        for (String title : ExcelUtil.getConfmap().get(1)) {
            Cell cell = rowTitle.createCell(index);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(title);
            index++;
        }

        if (map.size() > 0) {
            for (int rowi = 0; rowi < map.size(); rowi++) {
                Map<String, Object> obj = (Map<String, Object>) map.get(rowi);
                Row row = sheet.createRow(rowIndex);

                row.createCell(0).setCellValue(obj.get("ISCHARGED") == null ? "" : obj.get("ISCHARGED") + "");
                row.createCell(1).setCellValue(obj.get("ORDERNO") == null ? "" : obj.get("ORDERNO") + "");
                row.createCell(2).setCellValue(obj.get("NAME") == null ? "" : obj.get("NAME") + "");
                row.createCell(3).setCellValue(obj.get("BIG_PID") == null ? ""
                    : obj.get("BIG_PID") + "" + " - " + obj.get("SMALL_PID") == null ? "" : obj.get("SMALL_PID") + "");
                row.createCell(4).setCellValue(obj.get("TEL") == null ? "" : obj.get("TEL") + "");
                row.createCell(5).setCellValue(obj.get("RID") == null ? "" : obj.get("RID") + "");
                row.createCell(6).setCellValue(obj.get("COMNAME") == null ? "" : obj.get("COMNAME") + "");
                row.createCell(7).setCellValue(obj.get("SID") == null ? "" : obj.get("SID") + "");
                row.createCell(8).setCellValue(obj.get("FROMTYPE") == null ? "" : obj.get("FROMTYPE") + "");
                row.createCell(9).setCellValue(obj.get("PRODUCT") == null ? "" : obj.get("PRODUCT") + "");
                String depositStatus = obj.get("DEPOSITSTATUS") == null ? "" : obj.get("DEPOSITSTATUS").toString();
                if (depositStatus.equals("1")) {
                    row.createCell(10).setCellValue("无定金模式（普通来源）");
                } else if (depositStatus.equals("2")) {
                    row.createCell(10).setCellValue("已支付");
                } else if (depositStatus.equals("3")) {
                    row.createCell(10).setCellValue("未支付");
                } else if (depositStatus.equals("4")) {
                    row.createCell(10).setCellValue("已退回");
                } else {
                    row.createCell(10).setCellValue("");
                }
                row.createCell(11).setCellValue(obj.get("CREDIT_STATUS") == null ? "" : obj.get("CREDIT") + "-" + obj.get("CREDIT_STATUS"));
                row.createCell(12).setCellValue(obj.get("ISCANCLE") == null ? "" : obj.get("ISCANCLE").toString());
                row.createCell(13).setCellValue(obj.get("QDATE") == null ? "" : obj.get("QDATE") + "");
                row.createCell(14).setCellValue(obj.get("TITLE") == null ? "" : obj.get("TITLE") + "");
                row.createCell(15).setCellValue(obj.get("ALLOTDATE") == null ? "" : obj.get("ALLOTDATE") + "");

                row.createCell(16).setCellValue(obj.get("FDATE") == null ? "" : obj.get("FDATE") + "");
                row.createCell(17).setCellValue(obj.get("FDETAIL") == null ? "" : obj.get("FDETAIL") + "");
                row.createCell(18).setCellValue(obj.get("SDATE") == null ? "" : obj.get("SDATE") + "");
                row.createCell(19).setCellValue(obj.get("SDETAIL") == null ? "" : obj.get("SDETAIL") + "");
                row.createCell(20).setCellValue(obj.get("TDATE") == null ? "" : obj.get("TDATE") + "");
                row.createCell(21).setCellValue(obj.get("TDETAIL") == null ? "" : obj.get("TDETAIL") + "");
                row.createCell(22).setCellValue(obj.get("LDATE") == null ? "" : obj.get("LDATE") + "");
                row.createCell(23).setCellValue(obj.get("LDETAIL") == null ? "" : obj.get("LDETAIL") + "");
                row.createCell(24).setCellValue(obj.get("RANK") == null ? "" : obj.get("RANK") + "");
                if ((obj.get("RANK") == null ? "" : obj.get("RANK") + "").startsWith("C")) {
                    String reason = obj.get("REASON") == null ? "" : obj.get("REASON") + "";
                    if (reason.equals("1")) {
                        row.createCell(25).setCellValue("A 车型不匹配");
                    } else if (reason.equals("2")) {
                        row.createCell(25).setCellValue("B 金融方案不满意");
                    } else if (reason.equals("3")) {
                        row.createCell(25).setCellValue("C 风控原因  (审核未通过)");
                    } else if (reason.equals("4")) {
                        row.createCell(25).setCellValue(obj.get("REASONCONT") == null ? "" : "D (" + obj.get("REASONCONT") + ")");
                    } else {
                        row.createCell(25).setCellValue("");
                    }

                } else {
                    row.createCell(25).setCellValue("");
                }

                row.createCell(26).setCellValue(obj.get("STATUS") == null ? "" : obj.get("STATUS") + "");
                row.createCell(27).setCellValue(obj.get("FIRSTTIMECOMING") == null ? "" : obj.get("FIRSTTIMECOMING") + "");
                row.createCell(28).setCellValue(obj.get("IDD") == null ? "否" : obj.get("IDD").equals("1") ? "是" : "否");
                row.createCell(29).setCellValue(obj.get("INNDEPOSIT") == null ? "" : obj.get("INNDEPOSIT") + "");
                row.createCell(30).setCellValue(obj.get("LMTNUM") == null ? "" : ("" + obj.get("LMTNUM")).equals("0") ? "否" : "是");
                row.createCell(31).setCellValue(obj.get("INCOMEDATE") == null ? "" : obj.get("INCOMEDATE") + "");
                row.createCell(32).setCellValue(obj.get("ISINCOME") == null ? "否" : obj.get("ISINCOME").toString().equals("1") ? "是" : "否");
                row.createCell(33).setCellValue(obj.get("DEALDATE") == null ? "" : obj.get("DEALDATE") + "");
                row.createCell(34).setCellValue(obj.get("ISDEAL") == null ? "否" : obj.get("ISDEAL").toString().equals("1") ? "是" : "否");
                row.createCell(35).setCellValue(obj.get("GETCARDATE") == null ? "" : obj.get("GETCARDATE") + "");
                row.createCell(36).setCellValue(obj.get("ISGETCAR") == null ? "未提车" : obj.get("ISGETCAR") + "");
                row.createCell(37).setCellValue(obj.get("CONTRACTNO") == null ? "" : obj.get("CONTRACTNO").toString());
                row.createCell(38).setCellValue(obj.get("ISRECYCLE") == null ? "" : obj.get("ISRECYCLE").toString());

                String gift = obj.get("GIFT") == null ? null : obj.get("GIFT").toString();
                String gifts = "";
                if (gift != null) {
                    if (gift.indexOf("1") > -1) {
                        gifts += "到店礼";
                    }
                    if (gift.indexOf("2") > -1) {
                        gifts += ",订车礼";
                    }
                    if (gift.indexOf("3") > -1) {
                        gifts += ",交车礼";
                    }
                }
                if (gifts.startsWith(",")) gifts = gifts.substring(1);
                row.createCell(39).setCellValue(gifts);

                row.setRowStyle(contentStyle);
                rowIndex++;
            }
        }
        wb.write(out);
        return out.toByteArray();

    }

    /*
     * <p>Title: hastenTask</p> <p>Description: </p>
     * 
     * @param taskId
     * 
     * @param user
     * 
     * @return
     * 
     * @throws Exception
     * 
     * @see com.jiezh.content.leads.service.ClientService#hastenTask(java.lang.
     * String, com.jiezh.content.base.pub.author.AuthorUser)
     */
    @Override
    public Map<String, String> hastenTask(String taskId, AuthorUser user) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        LmurgeVO lmurgeVo = new LmurgeVO();

        if (taskId == null || "".equals(taskId)) {
            taskId = "0";
        }
        ClientVO cvo = clientDao.selectByPrimaryKey(Long.parseLong(taskId));
        String msg = "成功催促";
        if (cvo == null) {
            msg = "查找不到相关记录!";
        } else {
            if (cvo.getSid() < 1) {
                msg = "线索未分配给销售，无法催促!";
            } else {
                // 查找该任务销售用户
                UserVO userVo = DaoUtil.instance().cacheDao().getUserByKey(cvo.getSid());
                if (userVo == null) {
                    msg = "销售用户不存在,请联系管理员!";
                } else {
                    lmurgeVo.setUrgeAffairId(taskId);
                    lmurgeVo.setUrgeAffairContent(cvo.getClientName());
                    lmurgeVo.setUrgeToPersonId(userVo.getUserId()); // 销售用户ID
                    lmurgeVo.setUrgeToPersonName(userVo.getRealName());
                    lmurgeVo.setUrgeToOrganId(userVo.getOrganId());
                    lmurgeVo.setUrgeStatus("0");
                    List lmurgeList = lmurgeVODao.selectByLmurge(lmurgeVo);
                    if (lmurgeList.size() == 0) {
                        lmurgeVo.setUrgeFirstOrganId(user.getOrganId());
                        lmurgeVo.setUrgeFirstPersonId(user.getUserId());
                        lmurgeVo.setUrgeFirstPersonName(user.getRealName());
                        lmurgeVo.setUrgeOrganId(user.getOrganId());
                        lmurgeVo.setUrgePersonId(user.getUserId());
                        lmurgeVo.setUrgePersonName(user.getRealName());
                        lmurgeVo.setUrgeCreateDate(new Date());
                        lmurgeVo.setUrgeStatus("0");
                        lmurgeVo.setUrgeNum(1);
                        lmurgeVODao.insert(lmurgeVo);
                    } else {
                        LmurgeVO lmurgeUdadeVO = (LmurgeVO) lmurgeList.get(0);
                        lmurgeUdadeVO.setUrgeNum(lmurgeUdadeVO.getUrgeNum() + 1);
                        lmurgeUdadeVO.setUrgeOrganId(user.getOrganId());
                        lmurgeUdadeVO.setUrgePersonId(user.getUserId());
                        lmurgeUdadeVO.setUrgePersonName(user.getRealName());
                        lmurgeUdadeVO.setUrgeUpdateDate(new Date());
                        lmurgeUdadeVO.setUrgeStatus("0");
                        lmurgeVODao.updateByPrimaryKeySelective(lmurgeUdadeVO);
                    }
                    try {
                        // 查询应用
                        List<Map<String, Object>> agents =
                            com.jiezh.content.weixin.jieyi.pub.DaoUtil.instance().getAgentDao().getWxAgents(Integer.parseInt(WeixinConfig.getId()));
                        if (agents != null && agents.size() > 0) {
                            for (int i = 0; i < agents.size(); i++) {
                                String agentId = agents.get(i).get("AGENT_ID").toString();
                                News news = new News();
                                news.setTouser(userVo.getJzCode());
                                // news.setAgentid("3");
                                news.setAgentid(agentId);
                                news.setTitle("【回电催促提醒】");
                                news.setDescription(
                                    userVo.getRealName() + "，你的客户：" + cvo.getClientName() + "，电话：" + cvo.getTel() + "于今天多次来电，请及时给予回复，谢谢。");
                                String path = WeixinConfig.config.get(Parameters.URL_PREFIX).toString();
                                news.setUrl(path + "leads/exchange/wx/urge/viewInfo.do?clientId=" + cvo.getId() + "&agentType=3");

                                WeixinUtil.postNews(agentId, news);
                            }
                        }
                    } catch (Exception e) {

                    }

                    msg = "该条线索已对 " + userVo.getRealName() + " 进行催促!";
                }
            }
        }
        // add by houjq for jira JZLM-72
        if ("C".equals(cvo.getRank())) {
            cvo = new ClientVO();
            cvo.setId(Long.valueOf(taskId));
            cvo.setRank("");
            clientDao.updateByPrimaryKey(cvo);
        }

        map.put("msg", msg);
        return map;
    }

    // add by cj
    @Override
    public PageInfo<Map<String, Object>> getPlanList(int currenPage, Map<String, Object> paras) {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("month",
            paras.get("month") == null || "".equals(paras.get("month")) ? new SimpleDateFormat("yyyy-MM").format(new Date()) : paras.get("month"));
        Page<Map<String, Object>> page = null;
        try {
            lock.lock();
            if (clientDao.getPlanExistOfMonth(mp).equals("0")) {
                List<Map<String, Object>> user_List = clientDao.getUserList(mp);
                for (Map<String, Object> map : user_List) {
                    mp.put("user_id", map.get("USER_ID"));
                    clientDao.initPlan(mp);
                }
            }
            PageHelper.startPage(currenPage, Env.PAGE_SIZE);
            page = (Page<Map<String, Object>>) clientDao.getPlanList(paras);
            if (page == null) {
                page = new Page<Map<String, Object>>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return new PageInfo<Map<String, Object>>(page);

    }

    // add by cj
    @Override
    public Map<String, Object> getPlan(Map<String, Object> paras) {
        return clientDao.getPlan(paras);
    }

    // add by cj
    @Override
    public int updatePlan(Map<String, Object> result, Map<String, Object> paras) {
        clientDao.deletePlanByID(paras);
        Map<String, Object> pam = new HashMap<String, Object>();

        pam.put("user_id", paras.get("user_id"));
        pam.put("month", paras.get("month"));
        pam.put("plan_num", paras.get("plan_num"));
        if (result != null) {
            paras.put("first_tip", "0");
            paras.put("create_date", result.get("CREATE_DATE"));
            paras.put("perform", result.get("PERFORM"));
        } else {
            paras.put("first_tip", "0");
            paras.put("perform", "0");
        }
        return clientDao.insertPlan(paras);
    }

    // add by cj
    @Override
    public String getTaskState(Map<String, Object> paras) {
        Map<String, Object> result = clientDao.getPlan(paras);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        // 没有任务
        if (result == null) {
            return null;
        }
        // 任务没有设置
        else if ("0".equals(result.get("PLAN_NUM"))) {
            return null;
        } else {
            // 任务提示过
            if ("1".equals(result.get("FIRST_TIP"))) {
                return null;
            } else {
                int days = returnDays(new Date(), calendar.getTime());
                int plan_num = Integer.parseInt(result.get("PLAN_NUM") + "");// 任务量
                int perform = Integer.parseInt(result.get("PERFORM") + "");// 完成的任务量
                String mess = "";
                if (days == 0) {
                    mess = "最后一天了,";
                } else {
                    mess = "还有" + days + "天,";
                }

                if (days < 11 && (plan_num - perform) > 0) {
                    // 标记为已提醒
                    clientDao.updateTaskStateByID(Long.parseLong(result.get("ID") + ""));
                    return mess + "你还有" + (plan_num - perform) + "台车任务未完成,加油哦!";
                } else {
                    return null;
                }
            }
        }
    }

    // add by cj
    // 返回日期差
    private int returnDays(Date now, Date lastDay) {
        int days = 0;
        Calendar can1 = Calendar.getInstance();
        can1.setTime(now);
        Calendar can2 = Calendar.getInstance();
        can2.setTime(lastDay);
        int year1 = can1.get(Calendar.YEAR);
        int year2 = can2.get(Calendar.YEAR);
        Calendar can = null;
        if (can1.before(can2)) {
            days -= can1.get(Calendar.DAY_OF_YEAR);
            days += can2.get(Calendar.DAY_OF_YEAR);
            can = can1;
        } else {
            days -= can2.get(Calendar.DAY_OF_YEAR);
            days += can1.get(Calendar.DAY_OF_YEAR);
            can = can2;
        }
        for (int i = 0; i < Math.abs(year2 - year1); i++) {
            days += can.getActualMaximum(Calendar.DAY_OF_YEAR);
            can.add(Calendar.YEAR, 1);
        }
        return days;
    }

    @Override
    public List<Map<String, Object>> getTimeoutList(Map<String, Object> paras) {
        return clientDao.getTimeoutList(paras);
    }

    @Override
    public Object[] getScore(Map<String, Object> paras) {
        Object[] array = new Object[2];
        List<Map<String, Object>> list = clientDao.getScore(paras);
        for (Map<String, Object> map : list) {
            if (map.get("TYPE").equals("1")) {
                array[0] = map.get("SCORE");
            }
            if (map.get("TYPE").equals("2")) {
                array[1] = map.get("SCORE");
            }
        }
        return array;
    }

    @Override
    public int updateTaskByID(long id) {
        return clientDao.updateTimeTaskByID(id) + clientDao.updateUrgeTaskByID(id);
    }

    @Override
    public String getTotalScore(Map<String, Object> paras) {
        return clientDao.getTotalScore(paras);
    }

    @Override
    public int setScore(ClientVO vo, long user_id, String rankbefore) {
        // 获取当前时间
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("month", date);
        paras.put("user_id", user_id);
        paras.put("client_id", vo.getId());
        Map<String, Object> flags = clientDao.getIsExist(paras);
        boolean isTimeOutTask = clientDao.getIsTimeOutTask(paras).equals("0") ? false : true;
        // 没有加分或更新任务记录
        if (flags == null) {
            // 初始化积分任务
            paras.put("plan_state", "1");
            paras.put("score_state", "0");

            if (vo.getRank().equals("O")) {
                clientDao.insertPlanScore(paras);
                // 更新任务
                clientDao.updatePlan(paras);
            }
            // 成单
            if (vo.getRank().equals("O")) {
                // 积分状态
                paras.put("score_state", "1");
                // 废单转成单 +5 分
                if (/* rankbefore.equals("C") */vo.getInit_rank().equals("C")) {
                    paras.put("score", 5);
                    paras.put("score_type", "3");
                    // 更新积分表和积分流水表
                    clientDao.updateScoreLock(paras);
                    clientDao.updateScore(paras);
                }
                // +1分
                else {
                    if (isTimeOutTask) {
                        paras.put("score", 1);
                        paras.put("score_type", "2");
                        // 更新积分表
                        clientDao.updateScoreLock(paras);
                    }
                }
            } else {
                // 不是成单是超时任务 处理了也加一分
                if (isTimeOutTask) {
                    paras.put("score", 1);
                    paras.put("score_type", "2");
                    // 更新积分表
                    clientDao.updateScoreLock(paras);
                }
            }
        } else {
            // 任务未累加
            if (flags.get("PLAN_STATE").equals("0")) {
                if (vo.getRank().equals("O")) {
                    // 更新任务
                    clientDao.updatePlan(paras);
                    // 更新任务状态
                    paras.put("plan_state", "1");
                    paras.put("score_state", "1");
                    clientDao.updatePlanScore(paras);
                }
                // 成单
                if (vo.getRank().equals("O")) {
                    // 废单转成单 +5 分
                    if (/* rankbefore.equals("C") */vo.getInit_rank().equals("C")) {
                        paras.put("score", 5);
                        paras.put("score_type", "3");
                        // 更新积分表和积分流水表
                        clientDao.updateScoreLock(paras);
                        clientDao.updateScore(paras);
                    }
                    // +1分
                    else {
                        if (isTimeOutTask) {
                            paras.put("score", 1);
                            paras.put("score_type", "2");
                            // 更新积分表
                            clientDao.updateScoreLock(paras);
                        }
                    }
                } else {
                    // 不是成单是超时任务 处理了也加一分
                    if (isTimeOutTask) {
                        paras.put("score", 1);
                        paras.put("score_type", "2");
                        // 更新积分表
                        clientDao.updateScoreLock(paras);
                    }
                }
            } else {
                // 不是成单是超时任务 处理了也加一分
                if (isTimeOutTask) {
                    paras.put("score", 1);
                    paras.put("score_type", "2");
                    // 更新积分表
                    clientDao.updateScoreLock(paras);
                } else return 0;
            }
        }
        return 0;
    }

    @Override
    public int isInitPlan(ClientVO vo, long user_id) {
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("month", date);
        paras.put("user_id", user_id);
        Map<String, Object> result = clientDao.getPlan(paras);
        // 未指定计划 则初始化计划
        if (result == null) {
            paras.put("first_tip", "0");
            paras.put("perform", "0");
            paras.put("plan_num", "0");
            return clientDao.insertPlan(paras);
        }
        // 查看计划是否完成
        else {
            return 0;
        }
    }

    @Override
    public int initPlan(ClientVO vo, long user_id) {
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("month", date);
        paras.put("user_id", user_id);
        Map<String, Object> result = clientDao.getPlan(paras);

        String plan_num = result.get("PLAN_NUM") + "";
        String perform = result.get("PERFORM") + "";
        if ("0".equals(plan_num)) {
            return 0;
        } else {
            // 完成任务
            if (plan_num.equals(perform) && vo.getRank().equals("O")) {
                return 1;
            }
            // 未完成
            else {
                return 0;
            }
        }

    }

    @Override
    public String[] getU_TCount(Map<String, Object> paras) {
        String[] array = new String[2];
        List<Map<String, Object>> list = clientDao.getU_TCount(paras);
        if (list == null) {
            return new String[] {"0", "0"};
        }
        for (Map<String, Object> map : list) {
            String key = map.get("TYPE") + "";
            if (key.equals("u")) {
                array[0] = map.get("COUNT") + "";
            }
            if (key.equals("t")) {
                array[1] = map.get("COUNT") + "";
            }
        }
        return array;
    }

    @Override
    public String getIsExistsOfClient(String connum) {
        return clientDao.getIsExistsOfClient(connum);
    }

    @Override
    public void insertAllocationItem(Object... obj) {
        // obj[0] 线索ID array [1] 登陆用户id [2] 被分配人id
        Map<String, Object> paras = new HashMap<String, Object>();
        String[] ids = (String[]) obj[0];
        paras.put("user_id", obj[2]);
        paras.put("allocation_user_id", obj[1]);
        for (String id : ids) {
            paras.put("client_id", id);
            clientDao.insertAllocationItem(paras);
        }
    }

    // 跟踪回复统一处理
    @Override
    public String processClient(AuthorUser user, VistBean vistBean) {
        ClientVO clientVO = vistBean.getClientVO();
        if (clientVO.getRank().equals("C")) clientVO.setInit_rank("");
        // add by cj 是否初始化计划任务
        isInitPlan(clientVO, user.getUserId());
        // add by cj 加分逻辑
        setScore(clientVO, user.getUserId(), vistBean.getRankbefore());
        saveTrace(clientVO, vistBean.getClientTraceVO());

        long urgeid = vistBean.getUrgeid();
        // 来源标识 add by liangds 2016-01-19
        if (urgeid != 0) {
            LmurgeVO record = new LmurgeVO(); // 线索催促实体类
            record = lmurgeVODao.selectByPrimaryKey(urgeid);
            record.setUrgeEndDate(new Date());
            record.setUrgeStatus("1");
            lmurgeVODao.updateByPrimaryKey(record);
            // add by cj 更新处理后的任务状态
            updateTaskByID(clientVO.getId());
        } else {
            // add by cj 更新处理后的任务状态
            updateTaskByID(clientVO.getId());
        }

        // add by cj 任务是否完成
        int num = initPlan(clientVO, user.getUserId());

        return num > 0 ? "恭喜!本月任务已完成" : "已保存成功!";
    }

    @Override
    public byte[] exportToClient(Map<String, Object> paras) throws Exception {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            CellStyle headCellStyle = wb.createCellStyle();
            headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            // Sheet sheet = wb.createSheet("报表" + DateUtil.date2String(new
            // Date(),paras.get("status").equals("1")?"yyyy-MM-dd":"yyyy-MM"));
            Sheet sheet = wb.createSheet("报表" + paras.get("stnextdate") + "~" + paras.get("stnextdate1"));

            List<Map<String, Object>> resultList = this.clientDao.getExcelData(paras);

            if (resultList.isEmpty()) {
                setEmpInfo(wb, sheet);
                wb.write(out);
                return out.toByteArray();
            }

            // setTitle(wb,sheet,20,String.format("捷翊租赁电商销售组数据%s报表",
            // paras.get("status").equals("1")?"日":"月"));
            setTitle(wb, sheet, 20, "捷翊租赁电商销售组数据报表");
            setHeader(wb, sheet, ExcelUtil.getConfmap().get(2));
            CellStyle contentStyle = wb.createCellStyle();
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            Map<String, Map<String, Object>> tmp_type_1 = new LinkedHashMap<String, Map<String, Object>>();// 线索量
            Map<String, Map<String, Object>> tmp_type_2 = new LinkedHashMap<String, Map<String, Object>>();// 到店客户量
            Map<String, Map<String, Object>> tmp_type_3 = new LinkedHashMap<String, Map<String, Object>>();// 通过风控
            Map<String, Map<String, Object>> tmp_type_4 = new LinkedHashMap<String, Map<String, Object>>();// 成交客户
            Map<String, Map<String, Object>> tmp_type_5 = new LinkedHashMap<String, Map<String, Object>>();// 战败客户
            Map<String, Map<String, Object>> tmp_type_6 = new LinkedHashMap<String, Map<String, Object>>();// 战败成交量
            Map<String, Map<String, Object>> tmp_type_7 = new LinkedHashMap<String, Map<String, Object>>();// 首次邀约量
            Map<String, Map<String, Object>> tmp_type_8 = new LinkedHashMap<String, Map<String, Object>>();// 进件量
            Map<String, Map<String, Object>> tmp_type_9 = new LinkedHashMap<String, Map<String, Object>>();// 签约量

            // 分装数据
            for (Map<String, Object> map : resultList) {
                String type = map.get("TYPE") + "";
                HashMap<String, Object> mp = new HashMap<String, Object>();
                mp.put("NAME", map.get("NAME"));
                mp.put("REAL_NAME", map.get("REAL_NAME"));
                mp.put("NUM", map.get("NUM"));
                mp.put("ORGAN_ID", map.get("ORGAN_ID"));
                ;

                if (type.equals("1")) {
                    tmp_type_1.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("2")) {
                    tmp_type_2.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("3")) {
                    tmp_type_3.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("4")) {
                    tmp_type_4.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("5")) {
                    tmp_type_5.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("6")) {
                    tmp_type_6.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("7")) {
                    tmp_type_7.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("8")) {
                    tmp_type_8.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("9")) {
                    tmp_type_9.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else {

                }
            }

            // 自定义颜色
            HSSFPalette customPalette = wb.getCustomPalette();
            customPalette.setColorAtIndex(HSSFColor.ORANGE.index, (byte) 83, (byte) 152, (byte) 221);

            // cs1
            CellStyle bodyCs1 = wb.createCellStyle();
            bodyCs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            bodyCs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            // 背景颜色
            bodyCs1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            bodyCs1.setFillForegroundColor(HSSFColor.ORANGE.index);
            bodyCs1.setLocked(true);
            bodyCs1.setWrapText(true);

            // cs2
            CellStyle bodyCs2 = wb.createCellStyle();
            bodyCs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            bodyCs2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            bodyCs2.setLocked(true);
            bodyCs2.setWrapText(true);

            int sign = 2; // 标记开始行号
            int samerow = 0; // 相同行的计数器
            int tep = 0;
            int rowIndex = 2;
            int num = 1;
            int col = 0;
            Iterator<String> it = tmp_type_1.keySet().iterator();
            while (it.hasNext()) {
                sheet.setColumnWidth(col, 13 * 256);
                Row row = sheet.createRow(rowIndex);
                String key = it.next();
                Map<String, Object> data = tmp_type_1.get(key);

                int organId = Integer.valueOf(data.get("ORGAN_ID").toString()).intValue();
                if (tep != organId) {
                    if (samerow > 0) {
                        sheet.addMergedRegion(new CellRangeAddress(sign, sign + samerow - 1, 2, 2)); // 合并列
                    }
                    sign = rowIndex;
                    tep = organId;
                    samerow = 1;
                    num = 1;
                } else {
                    samerow++; // 相同加1
                    num++;// 序号
                }

                // 基础数据
                String city = data.get("NAME") + "";// 城市
                String person = data.get("REAL_NAME") + "";// 客户经理
                String num1 = data.get("NUM") + "";// 线索量
                String num2 = tmp_type_2.get(key) == null ? "0" : tmp_type_2.get(key).get("NUM") + "";// 到店客户量
                String num3 = tmp_type_3.get(key) == null ? "0" : tmp_type_3.get(key).get("NUM") + "";// 通过风控
                String num4 = tmp_type_4.get(key) == null ? "0" : tmp_type_4.get(key).get("NUM") + "";// 成交客户
                String num5 = tmp_type_5.get(key) == null ? "0" : tmp_type_5.get(key).get("NUM") + "";// 战败客户
                String num6 = tmp_type_6.get(key) == null ? "0" : tmp_type_6.get(key).get("NUM") + "";// 战败成交量
                String num7 = tmp_type_7.get(key) == null ? "0" : tmp_type_7.get(key).get("NUM") + "";// 首次邀约量
                String num13 = tmp_type_8.get(key) == null ? "0" : tmp_type_8.get(key).get("NUM") + "";// 进件量
                String num14 = tmp_type_9.get(key) == null ? "0" : tmp_type_9.get(key).get("NUM") + "";// 签约量
                // 计算数据
                String num8 = format.format(Double.parseDouble(num7) / Double.parseDouble(num1));// 首次跟进率
                String num9 = format.format(Double.parseDouble(num5) / Double.parseDouble(num1));// 战败率
                String num10 = format.format(Double.parseDouble(num2) / Double.parseDouble(num1));// 首次邀约到店量
                String num11 = format.format(Double.parseDouble(num3) / Double.parseDouble(num1));// 风控通过率
                String num12 = format.format(Double.parseDouble(num4) / Double.parseDouble(num1));// 成交率

                String num15 = format.format(Double.parseDouble(num13) / Double.parseDouble(num1));// 进件率
                String num16 = format.format(Double.parseDouble(num14) / Double.parseDouble(num1));// 签约率

                // 创建单元格
                if (data.get("REAL_NAME").equals("TOTAL")) {
                    // row.createCell(0).setCellValue("城市合计");
                    Cell cell = row.createCell(0);
                    cell.setCellValue("城市合计");
                    cell.setCellStyle(bodyCs1);
                } else {
                    row.createCell(0).setCellValue(num);
                }

                Cell cells = row.createCell(2);
                cells.setCellValue(city);
                cells.setCellStyle(bodyCs2);

                Cell cell3 = row.createCell(3);
                if (data.get("REAL_NAME").equals("TOTAL")) {
                    cell3.setCellValue("");
                    cell3.setCellStyle(bodyCs1);
                } else {
                    cell3.setCellValue(person);
                    cell3.setCellStyle(bodyCs2);
                }

                Cell cell4 = row.createCell(4);
                Cell cell5 = row.createCell(5);
                Cell cell6 = row.createCell(6);
                Cell cell7 = row.createCell(7);
                Cell cell8 = row.createCell(8);
                Cell cell9 = row.createCell(9);
                Cell cell10 = row.createCell(10);
                Cell cell11 = row.createCell(11);
                Cell cell12 = row.createCell(12);
                Cell cell13 = row.createCell(13);
                Cell cell14 = row.createCell(14);
                Cell cell15 = row.createCell(15);

                Cell cell16 = row.createCell(16);
                Cell cell17 = row.createCell(17);
                Cell cell18 = row.createCell(18);
                Cell cell19 = row.createCell(19);

                cell4.setCellValue(num1);
                cell5.setCellValue(num7);
                cell6.setCellValue(num8);
                cell7.setCellValue(num5);
                cell8.setCellValue(num9);
                cell9.setCellValue(num2);
                cell10.setCellValue(num10);
                cell11.setCellValue(num3);
                cell12.setCellValue(num11);
                cell13.setCellValue(num4);
                cell14.setCellValue(num12);
                cell15.setCellValue(num6);

                cell16.setCellValue(num13);
                cell17.setCellValue(num15);
                cell18.setCellValue(num14);
                cell19.setCellValue(num16);

                if (data.get("REAL_NAME").equals("TOTAL")) {
                    cell4.setCellStyle(bodyCs1);
                    cell5.setCellStyle(bodyCs1);
                    cell6.setCellStyle(bodyCs1);
                    cell7.setCellStyle(bodyCs1);
                    cell8.setCellStyle(bodyCs1);
                    cell9.setCellStyle(bodyCs1);
                    cell10.setCellStyle(bodyCs1);
                    cell11.setCellStyle(bodyCs1);
                    cell12.setCellStyle(bodyCs1);
                    cell13.setCellStyle(bodyCs1);
                    cell14.setCellStyle(bodyCs1);
                    cell15.setCellStyle(bodyCs1);

                    cell16.setCellStyle(bodyCs1);
                    cell17.setCellStyle(bodyCs1);
                    cell18.setCellStyle(bodyCs1);
                    cell19.setCellStyle(bodyCs1);
                } else {
                    cell4.setCellStyle(bodyCs2);
                    cell5.setCellStyle(bodyCs2);
                    cell6.setCellStyle(bodyCs2);
                    cell7.setCellStyle(bodyCs2);
                    cell8.setCellStyle(bodyCs2);
                    cell9.setCellStyle(bodyCs2);
                    cell10.setCellStyle(bodyCs2);
                    cell11.setCellStyle(bodyCs2);
                    cell12.setCellStyle(bodyCs2);
                    cell13.setCellStyle(bodyCs2);
                    cell14.setCellStyle(bodyCs2);
                    cell15.setCellStyle(bodyCs2);

                    cell16.setCellStyle(bodyCs2);
                    cell17.setCellStyle(bodyCs2);
                    cell18.setCellStyle(bodyCs2);
                    cell19.setCellStyle(bodyCs2);
                }
                rowIndex++;
                col++;
            }
            sheet.addMergedRegion(new CellRangeAddress(sign, sign + samerow - 1, (short) 2, (short) 2));
            wb.write(out);

        } finally {
            out.flush();
            out.close();
        }
        return out.toByteArray();
    }

    private void setTitle(HSSFWorkbook workbook, Sheet sheet, int cellLength, String str) {
        Font titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 25);
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setLocked(true);
        titleStyle.setWrapText(true);

        Row headertitle = sheet.createRow(0);
        headertitle.setHeightInPoints(50);
        Cell titleCell = headertitle.createCell(0);
        titleCell.setCellValue(new HSSFRichTextString(str));
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, (short) cellLength - 1));
    }

    private void setHeader(HSSFWorkbook workbook, Sheet sheet, String[] strs) {
        Font headFont = workbook.createFont();
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 12);
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setFont(headFont);
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 设置上边框
        headCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 设置下边框
        headCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN); // 设置做边框
        headCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN); // 设置右边框
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置一个单元格边框颜色
        headCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 背景颜色
        headCellStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headCellStyle.setFillPattern(IndexedColors.BLUE_GREY.getIndex());

        // create header
        Row header = sheet.createRow(1);
        for (int i = 0; i < strs.length; i++) {
            String strName = strs[i];
            Cell cell = header.createCell(i);
            cell.setCellValue(strName);
            cell.setCellStyle(headCellStyle);
        }
    }

    @Override
    public List<Map<String, Object>> getExcelData(Map<String, Object> paras) throws Exception {

        List<Map<String, Object>> finalList = new ArrayList<Map<String, Object>>();

        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        try {

            List<Map<String, Object>> resultList = this.clientDao.getExcelData(paras);
            Map<String, Map<String, Object>> tmp_type_1 = new LinkedHashMap<String, Map<String, Object>>();// 线索量
            Map<String, Map<String, Object>> tmp_type_2 = new LinkedHashMap<String, Map<String, Object>>();// 到店客户量
            Map<String, Map<String, Object>> tmp_type_3 = new LinkedHashMap<String, Map<String, Object>>();// 通过风控
            Map<String, Map<String, Object>> tmp_type_4 = new LinkedHashMap<String, Map<String, Object>>();// 成交客户
            Map<String, Map<String, Object>> tmp_type_5 = new LinkedHashMap<String, Map<String, Object>>();// 战败客户
            Map<String, Map<String, Object>> tmp_type_6 = new LinkedHashMap<String, Map<String, Object>>();// 战败成交量
            Map<String, Map<String, Object>> tmp_type_7 = new LinkedHashMap<String, Map<String, Object>>();// 首次邀约量
            Map<String, Map<String, Object>> tmp_type_8 = new LinkedHashMap<String, Map<String, Object>>();// 进件量
            Map<String, Map<String, Object>> tmp_type_9 = new LinkedHashMap<String, Map<String, Object>>();// 签约量

            // 分装数据
            for (Map<String, Object> map : resultList) {
                String type = map.get("TYPE") + "";
                HashMap<String, Object> mp = new HashMap<String, Object>();
                mp.put("NAME", map.get("NAME"));
                mp.put("REAL_NAME", map.get("REAL_NAME"));
                mp.put("NUM", map.get("NUM"));
                mp.put("ORGAN_ID", map.get("ORGAN_ID"));
                ;

                if (type.equals("1")) {
                    tmp_type_1.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("2")) {
                    tmp_type_2.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("3")) {
                    tmp_type_3.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("4")) {
                    tmp_type_4.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("5")) {
                    tmp_type_5.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("6")) {
                    tmp_type_6.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("7")) {
                    tmp_type_7.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("8")) {
                    tmp_type_8.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else if (type.equals("9")) {
                    tmp_type_9.put(map.get("USER_ID") + "@" + map.get("ORGAN_ID"), mp);
                } else {

                }
            }

            Iterator<String> it = tmp_type_1.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Map<String, Object> data = tmp_type_1.get(key);
                // 基础数据
                String city = data.get("NAME") + "";// 城市
                String person = data.get("REAL_NAME") + "";// 客户经理
                String num1 = data.get("NUM") + "";// 线索量
                String num2 = tmp_type_2.get(key) == null ? "0" : tmp_type_2.get(key).get("NUM") + "";// 到店客户量
                String num3 = tmp_type_3.get(key) == null ? "0" : tmp_type_3.get(key).get("NUM") + "";// 通过风控
                String num4 = tmp_type_4.get(key) == null ? "0" : tmp_type_4.get(key).get("NUM") + "";// 成交客户
                String num5 = tmp_type_5.get(key) == null ? "0" : tmp_type_5.get(key).get("NUM") + "";// 战败客户
                String num6 = tmp_type_6.get(key) == null ? "0" : tmp_type_6.get(key).get("NUM") + "";// 战败成交量
                String num7 = tmp_type_7.get(key) == null ? "0" : tmp_type_7.get(key).get("NUM") + "";// 首次邀约量
                String num13 = tmp_type_8.get(key) == null ? "0" : tmp_type_8.get(key).get("NUM") + "";// 进件量
                String num14 = tmp_type_9.get(key) == null ? "0" : tmp_type_9.get(key).get("NUM") + "";// 签约量
                // 计算数据
                String num8 = format.format(Double.parseDouble(num7) / Double.parseDouble(num1));// 首次跟进率
                String num9 = format.format(Double.parseDouble(num5) / Double.parseDouble(num1));// 战败率
                String num10 = format.format(Double.parseDouble(num2) / Double.parseDouble(num1));// 首次邀约到店量
                String num11 = format.format(Double.parseDouble(num3) / Double.parseDouble(num1));// 风控通过率
                String num12 = format.format(Double.parseDouble(num4) / Double.parseDouble(num1));// 成交率

                String num15 = format.format(Double.parseDouble(num13) / Double.parseDouble(num1));// 进件率
                String num16 = format.format(Double.parseDouble(num14) / Double.parseDouble(num1));// 签约率

                Map<String, Object> baseBap = new LinkedHashMap<String, Object>();

                // 创建单元格
                if (data.get("REAL_NAME").equals("TOTAL")) {
                    baseBap.put("city", city);
                    baseBap.put("person", "城市合计");
                } else {
                    baseBap.put("city", city);
                    baseBap.put("person", person);
                }
                baseBap.put("num1", num1);
                baseBap.put("num7", num7);
                baseBap.put("num8", num8);

                baseBap.put("num5", num5);
                baseBap.put("num9", num9);
                baseBap.put("num2", num2);
                baseBap.put("num10", num10);

                baseBap.put("num3", num3);
                baseBap.put("num11", num11);
                baseBap.put("num4", num4);
                baseBap.put("num12", num12);

                baseBap.put("num6", num6);
                baseBap.put("num13", num13);
                baseBap.put("num15", num15);
                baseBap.put("num14", num14);
                baseBap.put("num16", num16);

                finalList.add(baseBap);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getCause());
        }
        return finalList;
    }

    public byte[] exportReport(String stime, String etime, String organId) throws Exception {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // Sheet sheet = wb.createSheet("报表" + DateUtil.date2String(new
        // Date(),paras.get("status").equals("1")?"yyyy-MM-dd":"yyyy-MM"));
        Sheet sheet = wb.createSheet("报表");
        List<Map<String, Object>> list = this.analyzeRepleat(organId, stime, etime);

        if (list.isEmpty()) {
            setEmpInfo(wb, sheet);
            wb.write(out);
            return out.toByteArray();
        }

        setTitle(wb, sheet, 7, "追踪率报表" + stime + "~" + etime);
        setHeader(wb, sheet, ExcelUtil.getConfmap().get(3));
        // cellStyle
        CellStyle style1 = wb.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        CellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setFillForegroundColor(IndexedColors.YELLOW.index);

        int bodystartRow = 2;
        for (Map<String, Object> map : list) {
            List<?> data = (List<?>) map.get("itemList");

            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(bodystartRow, bodystartRow + data.size(), 0, 0));
            for (int i = 0; i < data.size(); i++) {
                Map<String, Object> item = (Map<String, Object>) data.get(i);
                Row row = sheet.createRow(i + bodystartRow);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(map.get("NAME") == null ? "" : map.get("NAME").toString());
                cell0.setCellStyle(style1);
                // row.createCell(0).setCellValue(map.get("NAME")==null?"":map.get("NAME").toString());
                row.createCell(1).setCellValue(item.get("REAL_NAME") == null ? "" : item.get("REAL_NAME").toString());
                row.createCell(2).setCellValue(stime + "~" + etime);
                row.createCell(3).setCellValue(item.get("TOTAL") == null ? "" : item.get("TOTAL").toString());
                row.createCell(4).setCellValue(item.get("RE_TOTAL") == null ? "" : item.get("RE_TOTAL").toString());
                row.createCell(5).setCellValue(item.get("UN_RE_TOTAL") == null ? "" : item.get("UN_RE_TOTAL").toString());
                row.createCell(6).setCellValue(item.get("PERCENT") == null ? "" : item.get("PERCENT").toString());
            }

            Row row = sheet.createRow(bodystartRow + data.size());
            Cell cell = row.createCell(1);
            cell.setCellStyle(style2);
            cell.setCellValue("总计");

            cell = row.createCell(2);
            cell.setCellValue("");
            cell.setCellStyle(style2);

            cell = row.createCell(3);
            cell.setCellStyle(style2);
            cell.setCellValue(map.get("TOTAL") == null ? "" : map.get("TOTAL").toString());

            cell = row.createCell(4);
            cell.setCellStyle(style2);
            cell.setCellValue(map.get("RE_TOTAL") == null ? "" : map.get("RE_TOTAL").toString());

            cell = row.createCell(5);
            cell.setCellStyle(style2);
            cell.setCellValue(map.get("UN_RE_TOTAL") == null ? "" : map.get("UN_RE_TOTAL").toString());

            cell = row.createCell(6);
            cell.setCellStyle(style2);
            cell.setCellValue(map.get("PERCENT") == null ? "" : map.get("PERCENT").toString());

            bodystartRow += data.size() + 1;
        }
        wb.write(out);
        out.close();
        out.close();
        return out.toByteArray();
    }

    /**
     * 得到滴滴合作报表
     */
    @Override
    public List<Map<String, Object>> getDDReport(Map<String, Object> paras) {

        List<Map<String, Object>> rList = clientDao.getDDReport(paras);

        return rList;
    }

    /**
     * 滴滴销售排行
     */
    @Override
    public List<Map<String, Object>> getDDRank(Map<String, Object> paras) {

        return clientDao.selectDDRank(paras);
    }

    /**
     * 滴滴来源考评
     */
    @Override
    public List<Map<String, Object>> getDDStatistics(Map<String, Object> paras) {
        return clientDao.selectDDStatistics(paras);
    }

    /**
     * 滴滴来源考评（平均时间）
     */
    @Override
    public List<Map<String, Object>> getDDStatisticsByTime(Map<String, Object> paras) {
        return clientDao.selectDDStatisticsByTime(paras);
    }

    @Override
    public List<Map<String, Object>> getServiceTrace(ClientVO clientVO) {
        return clientDao.selectServiceTrace(clientVO);
    }

    @Override
    public String processClientAndTrace(AuthorUser user, ClientVO clientVo, ClientTraceVO clientTraceVo) {
        clientVo.setQdate(new Date());
        clientVo.setSex("1");
        clientVo.setDdate(new Date());
        clientVo.setAllotdate(new Date());
        clientDao.insert(clientVo);
        clientTraceVo.settId(clientVo.getId());
        clientTraceVo.setRedate(new Date());
        clientTraceVo.setuId(user.getRealName());
        clientTraceDao.insert(clientTraceVo);
        return null;
    }

    @Override
    public ActivityConfigVO getActivityConf(Long id) {
        return activityConfigVODao.selectByPrimaryKey(id);
    }

}
