/**
 * 
 */
package com.jiezh.content.leads.assign.service.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.content.leads.assign.service.WaitingAssignService;
import com.jiezh.content.leads.mileage.util.ExcelUtil;
import com.jiezh.content.leads.service.ClientService;
import com.jiezh.dao.leads.client.ClientDao;
import com.jiezh.dao.leads.client.ClientVO;
import com.jiezh.dao.leads.clientimp.ClientImportVO;
import com.jiezh.dao.leads.clientimp.ClientImportVODao;

/**
 * 描述类的作用
 * 
 * @author houjiaqiang
 * @since 2016年9月1日 上午9:33:52
 */
@Service
public class WaitingAssignServiceImp implements WaitingAssignService {
    public Log log = LogFactory.getLog(WaitingAssignServiceImp.class);
    @Autowired
    ClientImportVODao clientImportVODao;
    @Autowired
    ClientDao clientDao;

    @Autowired
    ClientService clientService;

    @Override
    public PageInfo<ClientImportVO> getPageList(int curPage, ClientImportVO vo) {
        PageHelper.startPage(curPage, Env.PAGE_SIZE);
        Page<ClientImportVO> page = (Page<ClientImportVO>) clientImportVODao.selectByVo(vo);
        if (page == null) {
            page = new Page<>();
        }
        return new PageInfo<ClientImportVO>(page);
    }

    @Override
    public List<Map<String, Object>> getVoList(Map<String, Object> param) {
        return clientImportVODao.selectByMap(param);
    }

    @Override
    public int processAssign(AuthorUser user, Map<String, Object> param) {
        String[] ids = (String[]) param.get("ids");
        int count = 0;
        for (String id : ids) {
            ClientImportVO vo = clientImportVODao.selectByPrimaryKey(Long.valueOf(id));
            ClientVO clientVO = new ClientVO();
            clientVO.setClientName(vo.getClientName());
            clientVO.setTel(vo.getTel());
            clientVO.setRid(Integer.valueOf(param.get("rid").toString()));
            clientVO.setSex("1");
            clientVO.setPersonid(vo.getIdNo());
            clientVO.setTitle(vo.getCouponeCode());

            clientVO.setQdate(new Date());
            clientVO.setAllotdate(new Date());
            clientVO.setCompanyid(param.get("organId").toString());
            if (!StringUtils.isBlank(vo.getFromTypeBig())) {
                clientVO.setFromtypeBig(vo.getFromTypeBig());
                if (vo.getFromTypeBig().equals("2")) {
                    clientVO.setChannel(vo.getChannel());
                } else {
                    clientVO.setFromtype(vo.getFromType() == null ? 0 : Integer.valueOf(vo.getFromType()));
                }
            }
            clientDao.insert(clientVO);
            vo.setStatus("1");// 已分配
            vo.setUpdatedTime(new Date());
            vo.setUpdatedUserId(user.getUserId());
            clientImportVODao.updateByPrimaryKeySelective(vo);
            count++;
        }
        // 记录分配信息
        clientService.insertAllocationItem(ids, user.getUserId(), param.get("rid"));
        return count;
    }

    /**
     * 线索导入
     */
    public Map<String, Object> importExcel(InputStream in, String fileName, AuthorUser currenUser) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put(Env.ROW_START_KEY, 2);
            paraMap.put(Env.COL_START_KEY, "A");
            paraMap.put(Env.COL_END_KEY, "F");
            // 获取导入Excel数据
            List<List<Object>> dataList = ExcelUtil.importFromExcel(in, fileName, paraMap);
            in.close();
            // 保存导入数据
            if (dataList == null || dataList.size() == 0) {
                throw new Exception("没有获取到有效的导入数据！");
            }
            dataMap.put("errorMsg", saveImportDatas(dataList, currenUser));
            dataMap.put("page", getPageList(1, new ClientImportVO()));
        } catch (Exception e) {
            dataMap.put("msg", e.getMessage());
            log.error(e.getMessage(), e);
            throw e;
        }
        return dataMap;
    }

    // 处理导入数据，保存到数据库
    public byte[] saveImportDatas(List<List<Object>> rowList, AuthorUser currenUser) throws Exception {
        try {
            StringBuilder errorMsg = new StringBuilder();
            List<ClientImportVO> voList = new ArrayList<ClientImportVO>();
            Set<String> telSet = new HashSet<String>();
            for (int x = 0; x < rowList.size(); x++) {
                // 获取线束数据，转换成数据对象。
                List<Object> cellList = rowList.get(x);
                ClientImportVO importVO = new ClientImportVO();

                String clientName = (String) cellList.get(0);
                if (StringUtils.isBlank(clientName)) {
                    errorMsg.append("-第" + (x + 2) + "行数据的客户姓名不能为空！\r\n");
                } else {
                    importVO.setClientName(clientName);
                }

                String tel = (String) cellList.get(1);
                if (StringUtils.isBlank(tel)) {
                    errorMsg.append("-第" + (x + 2) + "行数据的手机号不能为空！\r\n");
                } else {
                    if (telSet.contains(tel)) {
                        errorMsg.append("-导入文件中存在多条手机号码为【" + tel + "】的用户，请修改后重新导入！\r\n");
                    } else {
                        telSet.add(tel);
                        int num = clientImportVODao.queryClientIsExistByTel(tel);
                        if (num > 0) {
                            errorMsg.append("-手机号码为【" + tel + "】的用户在系统中已存在，请勿重复导入！\r\n");
                        } else {
                            importVO.setTel(tel);
                        }
                    }
                }

                String sourceLabel = (String) cellList.get(2);
                if (StringUtils.isBlank(sourceLabel)) {
                    errorMsg.append("-第" + (x + 2) + "行数据的来源不能为空！\r\n");
                } else {
                    importVO.setFromType(getSourceCode(sourceLabel, errorMsg));
                }
                importVO.setCreatedTime(new Date());
                importVO.setCreatedUserId(currenUser.getUserId());
                importVO.setCity((String) cellList.get(3));
                importVO.setCouponeCode((String) cellList.get(4));
                importVO.setStatus("0");
                voList.add(importVO);
            }
            if (errorMsg.length() > 0) {
                return ("导入文件包含错误信息如下：\r\n" + errorMsg.toString()).getBytes();
            } else {
                clientImportVODao.insertByList(voList);
            }
            return null;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(StringUtils.isBlank(e.getMessage()) ? "导入文件数据与模板数据不一致，请重新导入！" : e.getMessage());
        }
    }

    public String getSourceCode(String label, StringBuilder errorMsg) throws Exception {
        String code = clientImportVODao.queryCodeByLabel(label);
        if (StringUtils.isBlank(code)) {
            errorMsg.append("-系统无法识别来源为【" + label + "】的数据，请核对后重新导入！\r\n");
        }
        return code;
    }
}
