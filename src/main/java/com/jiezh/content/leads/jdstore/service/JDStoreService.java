package com.jiezh.content.leads.jdstore.service;

import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.author.AuthorUser;
import com.jiezh.dao.leads.jdstore.JDStoreVO;

public interface JDStoreService {

    public JDStoreVO saveJDStoreTransaction(JDStoreVO storeVO, String oper, AuthorUser loginUser) throws Exception;

    public PageInfo<JDStoreVO> queryJDStoreTransactionList(int currentPage, JDStoreVO storeVO) throws Exception;

    public JDStoreVO queryJDStoreTransactionById(Long id) throws Exception;

    public byte[] createExcel(JDStoreVO queryVO) throws Exception;

}
