package com.jiezh.dao.leads.client;

import java.util.List;
import java.util.Map;

import com.jiezh.dao.leads.client.ProductVO;


public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProductVO record);

    ProductVO selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductVO record);

	List<Map<String, Object>> queryProducts();

	void queryRecord(long id);
	
	//add by cj
	List<Map<String, Object>> queryRootProducts();
	//add by cj
    List<ProductVO> queryChildProductsByPid(long pid);
}