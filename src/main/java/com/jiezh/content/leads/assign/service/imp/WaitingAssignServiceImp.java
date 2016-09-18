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
	    
            clientVO.setQdate(vo.getCreatedTime());
            clientVO.setAllotdate(new Date());
            clientVO.setCompanyid(param.get("organId").toString());
            if (!StringUtils.isBlank(vo.getFromTypeBig())) {
                clientVO.setFromtypeBig(vo.getFromTypeBig());
                if (vo.getFromTypeBig().equals("2")) {
                    clientVO.setChannel(vo.getChannel());
                } else {
                    clientVO.setFromtype(vo.getFromType() == null ? 0 : vo.getFromType().intValue());
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
    public Map<String, Object> importExcel(InputStream in, String fileName) {
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
            saveImportDatas(dataList);
            dataMap.put("page", getPageList(1, new ClientImportVO()));
            dataMap.put("msg", "导入成功！");
        } catch (Exception e) {
            dataMap.put("msg", e.getMessage());
            log.error(e.getMessage(), e);
        }
        return dataMap;
    }

    // 处理导入数据，保存到数据库
    public void saveImportDatas(List<List<Object>> rowList) throws Exception {
        try {
            List<ClientImportVO> voList = new ArrayList<ClientImportVO>();
            Set<String> telSet = new HashSet<String>();
            for (int x = 0; x < rowList.size(); x++) {
                // 获取线束数据，转换成数据对象。
                List<Object> cellList = rowList.get(x);
                ClientImportVO importVO = new ClientImportVO();

                String clientName = (String) cellList.get(0);
                if (StringUtils.isBlank(clientName)) {
                    throw new Exception("第" + (x + 2) + "行数据的客户姓名不能为空！");
                }
                importVO.setClientName(clientName);

                String tel = (String) cellList.get(1);
                if (StringUtils.isBlank(tel)) {
                    throw new Exception("第" + (x + 2) + "行数据的手机号不能为空！");
                } else {
                    if (telSet.contains(tel)) {
                        throw new Exception("导入文件中存在多条手机号码为【" + tel + "】的用户，请修改后重新导入！");
                    } else {
                        telSet.add(tel);
                        int num = clientImportVODao.queryClientIsExistByTel(tel);
                        if (num > 0) {
                            throw new Exception("手机号码为【" + tel + "】的用户在系统中已存在，请勿重复导入！");
                        }
                    }
                }
                importVO.setTel(tel);

                String fromTypeBig = (String) cellList.get(2);
                if (StringUtils.isBlank(fromTypeBig)) {
                    throw new Exception("第" + (x + 2) + "行数据的来源大类不能为空！");
                }
                importVO.setFromTypeBig(getSourceCode(fromTypeBig, "1044"));
                if (StringUtils.equals("互联网", fromTypeBig)) {
                    importVO.setFromType(Long.valueOf(getSourceCode((String) cellList.get(3), "1025")));
                } else if (StringUtils.equals("直销", fromTypeBig)) {
                    importVO.setFromType(Long.valueOf(getSourceCode((String) cellList.get(3), "1045")));
                } else {
                    importVO.setChannel((String) cellList.get(5));
                }
                importVO.setCity((String) cellList.get(4));
                importVO.setStatus("0");

                voList.add(importVO);
            }
            clientImportVODao.insertByList(voList);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception(StringUtils.isBlank(e.getMessage()) ? "导入文件数据与模板数据不一致，请重新导入！" : e.getMessage());
        }
    }

    public String getSourceCode(String label, String type) throws Exception {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("label", label);
        paraMap.put("type", type);
        paraMap.put("column", "CODE_ITEM_ID");
        if (StringUtils.equals("1044", type)) {
            paraMap.put("column", "VALUE");
        }
        try {
            String code = clientImportVODao.queryCodeByLabel(paraMap);
            if (code == null) {
                throw new Exception();
            }
            return code;
        } catch (Exception e) {
            throw new Exception("系统无法识别来源为【" + label + "】的数据，请核对后重新导入！");
        }
    }
}
