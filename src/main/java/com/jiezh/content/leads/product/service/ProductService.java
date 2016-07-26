package com.jiezh.content.leads.product.service;

import java.util.List;

import com.jiezh.content.base.pub.util.Node;
import com.jiezh.dao.leads.client.ProductVO;

/** 
* @ClassName: ProductService 
* @Description: 
* @author 
* @date 2016年2
*  
*/
public interface ProductService {
    
    int insert (ProductVO record);
    
    int update (ProductVO record);
    
    ProductVO selectByPrimaryKey(long id);
    //查询所有内容树
	String queryProducts();

	void deleteProduect(long parseInt);
	
	ProductVO queryRecord(long id);
	
	//add by cj 产品列表
	List<Node> queryProductList();
	
	List<ProductVO> queryChildProductsByPid(long pid);
}
