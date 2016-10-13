package com.jiezh.dao.leads.jdstore;

import java.util.List;

public interface JDStoreDao {

    public int insertJDStoreTransaction(JDStoreVO storeVO);

    public int updateJDStoreTransaction(JDStoreVO storeVO);

    public List<JDStoreVO> queryJDStoreTransactionList(JDStoreVO storeVO);

    public JDStoreVO queryJDStoreTransactionById(Long id);

}
