package com.jiezh.content.leads.assign.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.leads.clientimp.ClientImportVO;

/**
 * 待分配service接口
 * 
 * @author houjiaqiang
 * @since 2016年9月1日 上午9:31:34
 */
public interface WaitingAssignService {
    public PageInfo<ClientImportVO> getPageList(int curPage, ClientImportVO vo);

    public List<Map<String, Object>> getVoList(Map<String, Object> param);

    public int processAssign(AuthorUser user, Map<String, Object> param);

    public Map<String, Object> importExcel(InputStream in, String fileName, AuthorUser currenUser) throws Exception;

    public List<Map<String, Object>> queryCustomerListForAssign();
}
