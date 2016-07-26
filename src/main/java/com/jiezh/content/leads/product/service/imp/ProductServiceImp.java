package com.jiezh.content.leads.product.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiezh.content.base.pub.Env;
import com.jiezh.content.base.pub.util.Node;
import com.jiezh.content.base.pub.util.Tools;
import com.jiezh.content.leads.product.service.ProductService;
import com.jiezh.dao.leads.client.ProductVO;
import com.jiezh.dao.leads.client.ProductDao;

/** 
* @ClassName: ProductServiceImp 
* @Description: 产品内容
* @author wp
* @date 2016年2月
* 
*/
@Service("leads.urge.service.ProductService")
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductDao productDao;
    
    @Override
    public int insert(ProductVO record) {
    	return productDao.insert(record);
    }

    
    @Override
    public int update(ProductVO record) {
	return productDao.updateByPrimaryKey(record);
    }

    
    @Override
    public ProductVO selectByPrimaryKey(long id) {
	return productDao.selectByPrimaryKey(id);
    }

	@Override
	public String queryProducts() {
		List<Node> nodes = new ArrayList<Node>();

		Node node = null;
		List<Map<String, Object>> listR= null;
		// 得到列表  
		listR = productDao.queryProducts();
		for (Map<String, Object> obj : listR) {
			node = new Node();
			node.setId(obj.get("ID").toString());
			node.setName(obj.get("NAME").toString());
			node.setpId(obj.get("PID").toString());

			node.setOpen(false);

//			if ()
//				node.setChecked(true);

//			if (obj.get("CHE").equals("check"))
//				node.setNocheck(false);
//			else
//				node.setNocheck(true);
			// System.out.println(node.getId()+node.getName()+node.getPid()+node.getT());
			node.setIsParent(false);
			node.setNocheck(false);
			node.setChecked(false);
			nodes.add(node);
		}
		String str0 = Tools.listToJson(nodes);
		return str0;
	}


	@Override
	public void deleteProduect(long id) {
		productDao.deleteByPrimaryKey(id);
	}


	@Override
	public ProductVO queryRecord(long id) {
		return productDao.selectByPrimaryKey(id);
	}


	@Override
	public List<Node> queryProductList() {
		List<Node> nodes = new ArrayList<Node>();

		Node node = null;
		List<Map<String, Object>> listR= null;
		// 得到列表  
		listR = productDao.queryRootProducts();
		for (Map<String, Object> obj : listR) {
			node = new Node();
			node.setId(obj.get("ID").toString());
			node.setName(obj.get("NAME").toString());
			node.setpId(obj.get("PID").toString());
			node.setOpen(false);
			node.setIsParent(false);
			node.setNocheck(false);
			node.setChecked(false);
			nodes.add(node);
		}
		return nodes;
	}


	@Override
	public List<ProductVO> queryChildProductsByPid(long pid) {
		return productDao.queryChildProductsByPid(pid);
	}

}
